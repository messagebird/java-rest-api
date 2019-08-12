import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleCreateVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Please specify your access key and voice call flow arguments");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        final VoiceCallFlowRequest voiceCallFlow = new VoiceCallFlowRequest();

        voiceCallFlow.setTitle(args[2]);
        voiceCallFlow.setRecord(args[3]);
        voiceCallFlow.setSteps(args[4]); // VoiceStep Object
        voiceCallFlow.setDefaultCall(args[5]);

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

        System.out.print(voiceCallFlow.toString());
    }
}