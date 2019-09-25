import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleUpdateConversationWebhook {

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 8) {
            System.out.println("Please at least specify your access key and a max of 8 arguments.\n" +
                    "You can also specify a webhook id, enabled or not, url of webhook and events. Default value would be used if you don't\n" +
                    "Events can be a maximum of 4 between conversation.created, conversation.updated, message.created, message.updated\n" +
                    "If third argument is enabled, then status of the webhook will be updated to enabled, otherwise will be disabled\n\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) webhook_title(Optional) enabled(Optional) webhook-url(Optional) event1(Optional) event2(Optional) event3(Optional) event4(Optional)");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Creating webhook object to send client
            System.out.println("Updating conversation webhook..");

            ConversationWebhookUpdateRequest request = new ConversationWebhookUpdateRequest(
                    getStatus(args) ,
                    getWebhookUrl(args),
                    getConversationWebhookEvents(args)
            );

            //Sending ConversationWebhook update request
            final ConversationWebhook conversationWebhookResponse = messageBirdClient.updateConversationWebhook(args[1], request);
            //Display conversationWebhook response
            System.out.println(conversationWebhookResponse);
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }

    private static ConversationWebhookStatus getStatus(String[] args) {
        return (args.length <= 3 || args[2].equals("enabled")) ? ConversationWebhookStatus.ENABLED : ConversationWebhookStatus.DISABLED;
    }

    private static String getWebhookUrl(String[] args) {
        return args.length > 4 ? args[3] : "https://example-web-hook-url";
    }

    private static List<ConversationWebhookEvent> parseConversationWebhookEvents(String[] args) {
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

    private static List<ConversationWebhookEvent> getConversationWebhookEvents(String[] args) {
        if (args.length < 5)
            return Arrays.asList(ConversationWebhookEvent.CONVERSATION_CREATED, ConversationWebhookEvent.MESSAGE_CREATED);

        String[] arrayOfEvents = new String[args.length - 4];
        System.arraycopy(args, 4, arrayOfEvents, 0, args.length - 4);

        return parseConversationWebhookEvents(arrayOfEvents);
    }
}
