import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.IfMachineType;
import com.messagebird.objects.VoiceType;
import com.messagebird.objects.VoiceMessage;
import com.messagebird.objects.VoiceMessageResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvt on 1/7/15.
 */
public class ExampleSendVoiceMessage {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body example : java -jar <this jar file> test_accesskey 31612345678,3161112233 \"My message to be send\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Sending message:");
            final List<BigInteger> phones = new ArrayList<BigInteger>();
            for (final String phoneNumber : args[1].split(",")) {
                phones.add(new BigInteger(phoneNumber));
            }

            // Send a voice message using the VoiceMessage object
            final VoiceMessage vm = new VoiceMessage(args[2], phones);
            vm.setIfMachine(IfMachineType.hangup);
            vm.setVoice(VoiceType.male);
            vm.setLanguage("en-gb");

            final VoiceMessageResponse response = messageBirdClient.sendVoiceMessage(vm);
            System.out.println(response.toString());

        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        }
    }
}
