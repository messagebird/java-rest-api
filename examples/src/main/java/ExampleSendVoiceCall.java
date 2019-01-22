import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.voicecalls.VoiceCall;
import com.messagebird.objects.voicecalls.VoiceCallFlow;
import com.messagebird.objects.voicecalls.VoiceCallResponse;

import java.util.Collections;

public class ExampleSendVoiceCall {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key, one phone number : java -jar <this jar file> test_accesskey 31612345678 ");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {

            System.out.println("Sending voice call:");
            //Creating voice call object to send voice call. Source, destination and callFlow fields are required
            final VoiceCall voiceCall = new VoiceCall();
            voiceCall.setSource("31644556677");
            voiceCall.setDestination(args[1]);

            //Title and steps are required fields for creating callFlow
            final VoiceCallFlow voiceCallFlow = new VoiceCallFlow();
            voiceCallFlow.setTitle("Test title");
            //action is required
            VoiceStep voiceStep = new VoiceStep();
            voiceStep.setAction("say");

            //Creating a step to use in voice step array
            final VoiceStepOption voiceStepOption = new VoiceStepOption();
            voiceStepOption.setPayload("This is a journey into sound. Good bye!");
            voiceStepOption.setVoice(VoiceType.male.getValue());
            voiceStepOption.setLanguage("en-US");
            voiceStep.setOptions(voiceStepOption);

            voiceCallFlow.setSteps(Collections.singletonList(voiceStep));
            voiceCall.setCallFlow(voiceCallFlow);
            //Sending request to client
            final VoiceCallResponse response = messageBirdClient.sendVoiceCall(voiceCall);
            System.out.println(response.toString());

        } catch (UnauthorizedException | GeneralException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        }
    }
}