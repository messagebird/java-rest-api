import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.ChildAccountResponse;

import java.util.List;

public class ExampleGetChildAccounts {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key, offset, limit arguments");
            return;
        }

        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Get a child accounts of partner account");
            List<ChildAccountResponse> response = messageBirdClient.getChildAccounts(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
            System.out.println("response: " + response);
        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
