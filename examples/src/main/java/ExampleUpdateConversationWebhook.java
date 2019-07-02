import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleUpdateConversationWebhook {

    public static void main(String[] args) {
        if (args.length < 4 || args.length > 8) {
            System.out.println("Please specify your access key, webhook id, enabled or not, url of webhook and events.\n" +
                    "Events can be a maximum of 4 between conversation.created, conversation.updated, message.created, message.updated\n" +
                    "If third argument is enabled, then status of the webhook will be updated to enabled, otherwise will be disabled\n" +
                    "Usage : java -jar <this jar file> test_accesskey webhook_title webhook-url");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Creating webHook object to send client
            System.out.println("Updating conversation webhook..");

            String[] arrayOfEvents = new String[args.length - 4];
            System.arraycopy(args, 4, arrayOfEvents, 0, args.length - 4);

            ConversationWebhookUpdateRequest request = new ConversationWebhookUpdateRequest(
                    args[2].equals("enabled") ? ConversationWebhookStatus.ENABLED : ConversationWebhookStatus.DISABLED,
                    args[3],
                    parseConversationWebHookEvents(arrayOfEvents)
            );

            //Sending ConversationWebHook update request
            final ConversationWebhook conversationWebhookResponse = messageBirdClient.updateConversationWebhook(args[1], request);
            //Display conversationWebhook response
            System.out.println(conversationWebhookResponse.toString());
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

    }

    private static List<ConversationWebhookEvent> parseConversationWebHookEvents(String[] args) {
        List<ConversationWebhookEvent> conversationWebhookEventList = new ArrayList<>();

        for (String arg : args ) {
            ConversationWebhookEvent event = ConversationWebhookEvent.forValue(arg);

            if (event == null) {
                System.out.println("Skipping unrecognized event " + arg);
                continue;
            }

            conversationWebhookEventList.add(event);
        }

        return conversationWebhookEventList;
    }
}
