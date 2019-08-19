import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCallFlow;

public class ExampleViewVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a voice call flow ID");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Deleting voice call by id
            System.out.println("Requesting a Voice Call Flow");
            VoiceCallFlow voiceCallFlow = messageBirdClient
                .viewVoiceCallFlow(args[1]);
            System.out.println("Voice call flow retrieved ");

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}