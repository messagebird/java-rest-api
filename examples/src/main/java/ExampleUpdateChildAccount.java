import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleUpdateChildAccount {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key, name of child account, id of child account parameters");
            return;
        }

        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Updating a child account");
            messageBirdClient.updateChildAccount(args[1], args[2]);
            System.out.println("Child account is updated");
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
