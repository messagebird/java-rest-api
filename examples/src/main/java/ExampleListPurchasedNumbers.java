import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.PhoneNumberFeature;
import com.messagebird.objects.PurchasedNumbersFilter;

public class ExampleListPurchasedNumbers {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key.");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();
        filter.setLimit(25);

        try {
            System.out.println(messageBirdClient.listPurchasedNumbers(filter));
        } catch (UnauthorizedException | NotFoundException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}