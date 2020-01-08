import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleListNumbersForPurchase {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key.");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0], "https://numbers.messagebird.com");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        
        try {
            System.out.println(messageBirdClient.listNumbersForPurchase(args[1]));
            return;
        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}