import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Verify;
import com.messagebird.objects.VerifyRequest;

/**
 * Created by faizan on 10/12/15.
 */
public class ExampleSendVerifyToken {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a recipient : java -jar <this jar file> test_accesskey recipientNumber");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Send verify token
            System.out.println("sending verify token request:");
            VerifyRequest verifyRequest = new VerifyRequest(args[1]);
            verifyRequest.setTimeout(120);
            final Verify verify = messageBirdClient.sendVerifyToken(verifyRequest);
            System.out.println(verify.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
