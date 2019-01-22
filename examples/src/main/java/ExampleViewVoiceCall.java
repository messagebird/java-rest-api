import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCallResponse;

public class ExampleViewVoiceCall {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a call ID : java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add service to client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting a voice call");
            final String callId = args[1];
            //Sending call id parameter to client
            final VoiceCallResponse voiceCallResponse = messageBirdClient.viewVoiceCall(callId);
            //Display voice call response object
            System.out.println(voiceCallResponse.toString());

        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
