import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.PurchasedNumberCreatedResponse;

public class ExamplePurchaseNumber {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key, number, country code and billing interval months, eg: ExamplePurchaseNumber test_accesskey 3197010240563 NL 1");
            return;
        }
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0], "https://numbers.messagebird.com");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        
        try {
            PurchasedNumberCreatedResponse purchasedNumberCreatedResponse = messageBirdClient.purchaseNumber(args[1], args[2], Integer.parseInt(args[3]));

            System.out.println(purchasedNumberCreatedResponse);
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}