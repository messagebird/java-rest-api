import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleUpdateNumber {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, phone number, and the tags you wish to apply to it.");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        try {
            System.out.println(messageBirdClient.updateNumber(args[1], args[2], args[3], args[4]));
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
               System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}