import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.Webhook;
import com.messagebird.objects.voicecalls.WebhookResponseData;

public class ExampleSendWebhook {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, url and token of webhook :" +
                    " java -jar <this jar file> test_accesskey webhook-url webhook-token");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Creating webhook object to send client
            System.out.println("Creating new webhook..");
            final Webhook webhook = new Webhook();
            webhook.setUrl(args[1]);
            webhook.setToken(args[2]);
            //Sending webhook object to client
            final WebhookResponseData webhookResponseDataList = messageBirdClient.createWebhook(webhook);
            //Display webhook response
            System.out.println(webhookResponseDataList.toString());
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

    }
}
