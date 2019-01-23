import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.WebhookResponseData;

public class ExampleViewWebhook {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key, webhook id :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Viewing webhook..");
            final String webHookId = args[1];
            //Viewing webHook by webHook id
            final WebhookResponseData webHookResponseDataList = messageBirdClient.viewWebHook(webHookId);
            //Display WebHook Response Data
            System.out.println(webHookResponseDataList.toString());
        } catch (GeneralException | UnauthorizedException | NotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
