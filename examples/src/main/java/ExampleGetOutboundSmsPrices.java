import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.MessageBirdException;
import com.messagebird.objects.OutboundSmsPriceResponse;

public class ExampleGetOutboundSmsPrices {

    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) {
            System.out.println("Please specify your access key and (optionally) SMPP username");
            return;
        }

        final MessageBirdClient messageBirdClient = new MessageBirdClient(new MessageBirdServiceImpl(args[0]));

        try {
            System.out.println("Get a list of outbound SMS prices");

            final OutboundSmsPriceResponse outboundSmsPriceResponse;

            if (args.length == 2) {
                final String smppUsername = args[1];
                outboundSmsPriceResponse = messageBirdClient.getOutboundSmsPrices(smppUsername);
            } else {
                outboundSmsPriceResponse = messageBirdClient.getOutboundSmsPrices();
            }

            System.out.println("response: " + outboundSmsPriceResponse);
        } catch (MessageBirdException e) {
            e.printStackTrace();
        }
    }
}
