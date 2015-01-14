import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageList;

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
            // Get Balance
            System.out.println("Delete message:");
            messageBirdClient.deleteMessage(args[1]);
            System.out.println("Message ["+args[1]+"] deleted.");

        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors()!=null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        } catch (NotFoundException notFoundException) {
            if (notFoundException.getErrors() !=null) {
                System.out.println(notFoundException.getErrors().toString());
            }
            notFoundException.printStackTrace();
        }
    }
}
