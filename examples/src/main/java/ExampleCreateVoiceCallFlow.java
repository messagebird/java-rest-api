import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleCreateVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key and voice call flow arguments");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        final VoiceCallFlowRequest voiceCallFlow = new VoiceCallFlowRequest();

        voiceCallFlow.setTitle(args[1]);
        voiceCallFlow.setRecord(args[2]);
        voiceCallFlow.setSteps(args[3]); // VoiceStep Object
        voiceCallFlow.setDefaultCall(args[4]);

        try {
            //Deleting voice call by id
            System.out.println("Creting a Voice Call Flow");
            messageBirdClient.sendVoiceCallFlow(voiceCallFlow);
            System.out.println("Voice call flow created");

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}