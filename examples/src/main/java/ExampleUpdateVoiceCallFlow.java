import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.VoiceStep;
import com.messagebird.objects.voicecalls.VoiceCallFlowRequest;
import com.messagebird.objects.voicecalls.VoiceCallFlow;
import java.util.Collections;

public class ExampleUpdateVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key and voice call flow arguments");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        final VoiceCallFlowRequest voiceCallFlowRequest = new VoiceCallFlowRequest();

        voiceCallFlowRequest.setTitle(args[2]);
        voiceCallFlowRequest.setRecord(true); // Can be false as well, see docs
        VoiceStep voiceStep = new VoiceStep();
        voiceCallFlowRequest.setSteps(Collections.singletonList(voiceStep)); // VoiceStep Object
        voiceCallFlowRequest.setDefaultCall(true); // Can be false as well, see docs

        try {
            //Deleting voice call by id
            System.out.println("Updating a Voice Call Flow");
            VoiceCallFlow voiceCallFlow = messageBirdClient
                .updateVoiceCallFlow(args[1], voiceCallFlowRequest)
                .getData()
                .get(0);
            System.out.println("Voice call flow updated");

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

        System.out.print(voiceCallFlowRequest.toString());
    }
}