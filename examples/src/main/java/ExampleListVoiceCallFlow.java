import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.objects.MessageResponse;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
 * @TODO - not working, needs implementation
 */
public class ExampleListVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key, example : java -jar <this jar file> test_accesskey");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the cligient
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get list of call flows with offset and limit
            System.out.println("Retrieving message list");
            final MessageList messageList = messageBirdClient.listCallFlow(3, null);

            // Display balance
            System.out.println(messageList.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
