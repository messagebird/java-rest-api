import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Lookup;

import java.math.BigInteger;

public class ExampleViewLookup {

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
            // View Lookup
            System.out.println("Viewing Lookup:");
            final Lookup lookupRequest = new Lookup(new BigInteger(args[1]));
            // Optionally set a country code (in the case of national numbers)
            if (args.length > 2 && args[2] != null && !args[2].isEmpty()) {
                lookupRequest.setCountryCode(args[2]);
            }

            final Lookup lookup = messageBirdClient.viewLookup(lookupRequest);
            System.out.println(lookup.toString());
        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }

            exception.printStackTrace();
        }
    }
}
