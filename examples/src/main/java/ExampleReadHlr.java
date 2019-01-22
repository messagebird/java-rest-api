import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.objects.Hlr;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

import java.math.BigInteger;

/**
 * Created by rvt on 1/7/15.
 */
public class ExampleReadHlr {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Please specify your access key and phone in that order example : java -jar <this jar file> test_accesskey 31612345678");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Retrieving HLR:");
            final Hlr hlr1 = messageBirdClient.getRequestHlr(new BigInteger(args[1]), "ExampleReadHlr reference");
            System.out.println(hlr1.toString());

            // Get Hlr using the id only
            System.out.println("Now using returned id to get Hlr:");
            final Hlr hlr2 = messageBirdClient.getViewHlr(hlr1.getId());
            System.out.println(hlr2.toString());

        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
