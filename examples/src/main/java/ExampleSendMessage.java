import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.objects.MessageResponse;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvt on 1/7/15.
 */
public class ExampleSendMessage {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body example : java -jar <this jar file> test_accesskey 31612345678,3161112233 \"My message to be send\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Sending message:");
            final List<BigInteger> phones = new ArrayList<>();
            for (final String phoneNumber : args[1].split(",")) {
                phones.add(new BigInteger(phoneNumber));
            }

            final MessageResponse response = messageBirdClient.sendMessage("MessageBird", args[2], phones);
            //Display message response
            System.out.println(response.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
