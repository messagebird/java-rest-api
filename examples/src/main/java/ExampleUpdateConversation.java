import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

public class ExampleUpdateConversation {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, the ID of a conversation and the status to update the conversation to." +
                    " Example : java -jar <this jar file> test_accesskey test_conversationId archived");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        final ConversationStatus newStatus = ConversationStatus.forValue(args[2]);
        try {
            final Conversation response = messageBirdClient.updateConversation(args[1], newStatus);
            // Display message response
            System.out.println(response.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
