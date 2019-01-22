import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

/**
 * Created by rvt on 1/8/15.
 */
public class ExampleDeleteMessage {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please specify your access key example and a id to delete: java -jar <this jar file> test_accesskey 0f15f050454ad3db04286d6b30005106");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Deleting message by id
            System.out.println("Delete message:");
            messageBirdClient.deleteMessage(args[1]);
            System.out.println("Message ["+args[1]+"] deleted.");

        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
