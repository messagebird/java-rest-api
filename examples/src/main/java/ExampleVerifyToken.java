import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Verify;

/**
 * Created by faizan on 10/12/15.
 */
public class ExampleVerifyToken {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, verifyId and a token : java -jar <this jar file> test_accessKey verifyId token");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);


        try {
            // Send verify token
            System.out.println("verifying token request: " + args[2]);
            final Verify verify = messageBirdClient.verifyToken(args[1], args[2]);
            System.out.println(verify.toString());
        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
