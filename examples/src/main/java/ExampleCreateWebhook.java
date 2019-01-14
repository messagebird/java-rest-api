import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.Webhook;
import com.messagebird.objects.voicecalls.WebhookResponseData;

public class ExampleCreateWebhook {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, title of webhook and url of webhook :" +
                    " java -jar <this jar file> test_accesskey webhook_title webhook-url");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Creating new webhook..");
            final Webhook webhook = new Webhook();
            webhook.setTitle(args[1]);
            webhook.setUrl(args[2]);
            WebhookResponseData webhookResponseDataList = messageBirdClient.createWebhook(webhook);
            System.out.println(webhookResponseDataList.toString());
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

    }
}
