import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCallFlowList;

public class ExampleListVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key, example : java -jar <this jar file> test_accesskey");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the cligient
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get list of call flows with offset and limit
            System.out.println("Retrieving call flows list");
            VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(0, 0);

            // Display balance
            System.out.println(voiceCallFlowList.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            exception.printStackTrace();
        }
    }
}
