import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.LookupHlr;

import java.math.BigInteger;

public class ExampleRequestLookupHlr {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Please specify your access key, phone and reference in that order example : java -jar <this jar file> test_accesskey 31612345678 reference");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Request Lookup HLR
            System.out.println("Requesting Lookup HLR:");
            final LookupHlr lookupHlrRequest = new LookupHlr();
            lookupHlrRequest.setPhoneNumber(new BigInteger(args[1]));
            lookupHlrRequest.setReference(args[2]);
            // Optionally set a country code (in the case of national numbers)
            if (args.length > 3 && args[3] != null && !args[3].isEmpty()) {
                lookupHlrRequest.setCountryCode(args[3]);
            }
            final LookupHlr lookupHlr = messageBirdClient.requestLookupHlr(lookupHlrRequest);
            System.out.println(lookupHlr.toString());
        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors()!=null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() !=null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        }
    }
}
