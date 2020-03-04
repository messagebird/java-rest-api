import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageList;
import java.util.LinkedHashMap;
import java.util.Map;


public class ExampleListMessagesFiltered {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please specify your access key example : java -jar <this jar file> test_accesskey");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get list of messages with offset and limit
            System.out.println("Retrieving message list");
            
            // Create filters
            Map<String, Object> filters = new LinkedHashMap<>();
            filters.put("status", "scheduled");

            final MessageList messageList = messageBirdClient.listMessagesFiltered(3, null, filters);

            // Display messages
            System.out.println(messageList.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
