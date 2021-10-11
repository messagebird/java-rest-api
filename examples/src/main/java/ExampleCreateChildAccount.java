import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.ChildAccountRequest;
import com.messagebird.objects.ChildAccountCreateResponse;

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
            final ChildAccountRequest childAccountRequest = new ChildAccountRequest();
            childAccountRequest.setName(args[1]);
            ChildAccountCreateResponse childAccount = messageBirdClient.createChildAccount(childAccountRequest);
            System.out.println("Child account is created: " + childAccount.toString());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
