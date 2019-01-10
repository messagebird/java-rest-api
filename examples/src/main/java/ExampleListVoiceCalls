import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.VoiceCallResponseList;

public class ExampleListVoiceCalls {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please specify your access key example : java -jar <this jar file> test_accesskey");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Retrieving voice call list");
            final VoiceCallResponseList voiceCallResponseList = messageBirdClient.listAllVoiceCalls();
            System.out.println(voiceCallResponseList.toString());

        } catch (GeneralException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }
}