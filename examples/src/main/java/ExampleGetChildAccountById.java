import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.ChildAccountDetailedResponse;

public class ExampleGetChildAccountById {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and id of child account arguments");
            return;
        }

        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Get a child account by id");
            ChildAccountDetailedResponse response = messageBirdClient.getChildAccountById(args[1]);
            System.out.println("Response: " + response.toString());
        } catch (GeneralException | UnauthorizedException | NotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
