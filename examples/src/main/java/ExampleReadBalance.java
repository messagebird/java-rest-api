import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Balance;

/**
 * Created by rvt on 1/5/15.
 */
public class ExampleReadBalance {

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
            // Get Balance
            System.out.println("Retrieving your balance:");
            final Balance balance = messageBirdClient.getBalance();

            // Display balance
            System.out.println(balance.toString());

        } catch (UnauthorizedException | GeneralException | NotFoundException  exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
