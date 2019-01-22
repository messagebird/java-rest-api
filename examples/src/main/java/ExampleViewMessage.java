import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageResponse;

/**
 * Created by rvt on 1/7/15.
 */
public class ExampleViewMessage {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a message ID : java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("getting message info message:");
            final MessageResponse response = messageBirdClient.viewMessage(args[1]);
            System.out.println(response.toString());

        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
