import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleCreateChildAccount {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and name of child account arguments");
            return;
        }

        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Creating a child account of partner accounts");
            messageBirdClient.createChildAccount(args[1]);
            System.out.println("Child account is created");

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}