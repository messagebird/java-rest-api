import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCallResponseList;

public class ExampleListVoiceCalls {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Please specify your access key example, page, page size : java -jar <this jar file> test_accesskey 1 2");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Getting list of message with given page and page size parameters
            System.out.println("Retrieving voice call list");
            final Integer page = Integer.valueOf(args[1]);
            final Integer pageSize = Integer.valueOf(args[2]);
            final VoiceCallResponseList voiceCallResponseList = messageBirdClient.listAllVoiceCalls(page, pageSize) ;
            //Display result
            System.out.println(voiceCallResponseList.toString());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}