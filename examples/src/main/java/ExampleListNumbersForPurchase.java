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

import java.util.EnumSet;;

public class ExampleListNumbersForPurchase {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key.");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        
        try {
            if (args[1].equalsIgnoreCase("--params")) {
                PhoneNumbersLookup options = new PhoneNumbersLookup();
                options.setFeatures(EnumSet.of(PhoneNumberFeature.VOICE, PhoneNumberFeature.SMS));
                options.setType(PhoneNumberType.MOBILE);
                options.setLimit(10);
                options.setNumber(562);
                options.setSearchPattern(PhoneNumberSearchPattern.START);
                System.out.print(options.toString());
                try {
                    System.out.println(String.format("Request Made With Params: %s", messageBirdClient.listNumbersForPurchase("US", options)));
                } catch (IllegalAccessException exception) {
                    System.out.println(exception.toString());
                }
            } else {
                System.out.println(String.format("Request Made Without Params: %s", messageBirdClient.listNumbersForPurchase("NL")));
            }
        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
               System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}