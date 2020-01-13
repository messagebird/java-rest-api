import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.PhoneNumberFeature;
import com.messagebird.objects.PhoneNumberType;
import com.messagebird.objects.PhoneNumberSearchPattern;
import com.messagebird.objects.PhoneNumbersLookup;

public class ExampleListNumbersForPurchase {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a country code to test.");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        
        try {
            if (args.length > 2) {
                PhoneNumbersLookup options = new PhoneNumbersLookup();
                options.setFeatures(PhoneNumberFeature.VOICE, PhoneNumberFeature.SMS);
                options.setType(PhoneNumberType.MOBILE);
                options.setLimit(10);
                options.setNumber(562);
                options.setSearchPattern(PhoneNumberSearchPattern.START);
                System.out.print(options.toString());
                System.out.println(String.format("Request Made With Params: %s", messageBirdClient.listNumbersForPurchase("US", options)));
            } else {
                System.out.println(String.format("Request Made Without Params: %s", messageBirdClient.listNumbersForPurchase(args[1])));
            }
        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
               System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}