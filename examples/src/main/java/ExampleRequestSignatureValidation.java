import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.RequestValidator;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.RequestValidationException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import util.HttpHandlerHelpers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by hasselbach
 *
 * For exposing your application for external calls (webhooks from MessageBird)
 * you can use localtunnel (requires NodeJS).
 *
 * Here is example of usage:
 *
 * Install localtunnel globally:
 *  npm install -g localtunnel
 *
 * Select a free port: 3000, for example
 *  export MBEXAMPLEPORT=3000
 *
 * Run:
 *   lt --port $MBEXAMPLEPORT
 *
 * It will show you something like this:
 *   your url is: https://loud-yak-31.loca.lt
 *
 * * NOTE * you should not terminate this process, so next operations should be done in the other terminal session
 *
 * Remember the address from output:
 *  export FORWARDING_URL=https://loud-yak-31.loca.lt
 *
 * Take your access and secret key from Dashboard:
 *   secret key from https://dashboard.messagebird.com/en/developers/settings
 *   access key from https://dashboard.messagebird.com/en/developers/access
 *      *NOTE* you should use `Live` key for receiving webhooks
 *
 * Run this example as:
 *   java -jar <this jar file> $MBEXAMPLEPORT test_accesskey test_secret $FORWARDING_URL
 *
 * Now you able to play:
 *  send an SMS:
 *      curl "http://localhost:${MBEXAMPLEPORT}/send?recipients=XXXXXX&message=Hello%20Developer&originator=MBExample" && echo
 *          where XXXXXX is your phone number
 *  and then you will see in example app output:
 *      New request:
 * 	       GET /webhook?id=ee2d02749a6fb78a572bd7ce9118dff&mccmnc=20409&ported=0&recipient=XXXXXX&reference=example-server&status=delivered&statusDatetime=2019-01-10T09%3A23%3A03%2B00%3A00
 *      Message for XXXXXX is delivered
 *      Request has valid signature
 *
 * Description of webhook parameters can be found on
 *   https://developers.messagebird.com/docs/sms-messaging#handle-a-status-report
 *
 */
public class ExampleRequestSignatureValidation {

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println(
                    "Please specify port for this web application, access and secret keys," +
                            "and webhook url on which this application will be exposed:" +
                            "java -jar <this jar file> " +
                            "http_port test_accesskey test_secret http://this.application.endpoint/webhook");
            return;
        }

        try {
            int serverPort = Integer.parseInt(args[0]);
            String apiKey = args[1];
            String apiSecret = args[2];
            String forwardURL = args[3];
            URL reportURL = new URL(forwardURL + "/webhook");

            RequestValidator reqValidator = new RequestValidator(apiSecret);

            // Creating MessageBird client
            final MessageBirdService wsr = new MessageBirdServiceImpl(apiKey);
            final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

            HttpServer httpServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
            httpServer.createContext("/webhook", new MessageBirdWebhookHandler(reqValidator, forwardURL));
            httpServer.createContext("/send", new MessageBirdSender(messageBirdClient, reportURL));


            httpServer.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Here is most interesting thing happens
     * We get headers that MessageBird sent to us and validate whole request with them
     */
    static class MessageBirdWebhookHandler extends HttpHandlerHelpers implements HttpHandler {

        private final RequestValidator reqValidator;
        private final String baseURL;

        MessageBirdWebhookHandler(RequestValidator reqSigner, String baseURL) {
            this.reqValidator = reqSigner;
            this.baseURL = baseURL;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("New request:");

            try {
                String requestSignature = he.getRequestHeaders().getFirst(RequestValidator.SIGNATURE_HEADER);
                String requestURL = URI.create(baseURL).resolve(he.getRequestURI()).toString();
                byte[] requestBody = readAllBytes(he.getRequestBody());

                printRequest(
                        he.getRequestMethod(),
                        he.getRequestURI().toString(),
                        new String(requestBody, StandardCharsets.UTF_8)
                );

                reqValidator.validateSignature(requestSignature, requestURL, requestBody);

                // then only if signature is valid we can look at what is sent
                // for SMS status parameters can be found on https://developers.messagebird.com/docs/sms-messaging#handle-a-status-report
                reportStatus(parseQuery(he.getRequestURI().getQuery()));

                // MessageBird expects for `200 OK` status
                // otherwise, MessageBird will retry this request limited times
                sendResponse(he, 200, "Ok");
                System.out.println("Request has valid signature");
            } catch (RequestValidationException e) {
                sendResponse(he, 401, "Signature is invalid");
                System.out.println("Request has invalid signature: " + e.getMessage());
            } catch (Exception e) {
                sendResponse(he, 500, e.getMessage());
            }
        }

        private void reportStatus(Map<String, String> queryParams) {
            String recipient = queryParams.get("recipient");
            String status = queryParams.get("status");

            System.out.printf("Message for %s is %s", recipient, status);
        }
    }

    /**
     * Simple endpoint for sending SMS-messages via MessageBird
     *
     * @see ExampleSendMessage simplified example for message sending
     */
    static class MessageBirdSender extends HttpHandlerHelpers implements HttpHandler {

        private final MessageBirdClient messageBirdClient;
        private final URL reportUrl;

        MessageBirdSender(MessageBirdClient messageBirdClient, URL reportUrl) {
            this.messageBirdClient = messageBirdClient;
            this.reportUrl = reportUrl;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            Map<String, String> queryParams = parseQuery(he.getRequestURI().getQuery());
            String messageText = queryParams.get("message");
            String recipients = queryParams.get("recipients");
            String originator = queryParams.get("originator");

            Message message = new Message(originator, messageText, recipients);
            // reference MUST be set for receiving webhooks
            message.setReference("example-server");
            // reportUrl CAN be set for individual message
            // and also can be defined globally on
            // https://dashboard.messagebird.com/en/developers/settings
            message.setReportUrl(reportUrl);

            try {
                messageBirdClient.sendMessage(message);
                sendResponse(he, 201, "Message sent");
            } catch (GeneralException | UnauthorizedException e) {
                sendResponse(he, 500, e.getMessage());
                throw new IOException(e);
            }
        }
    }
}
