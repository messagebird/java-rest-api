import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.VoiceCallData;
import com.messagebird.objects.voicecalls.VoiceCallLeg;
import com.messagebird.objects.voicecalls.VoiceCallLegResponse;

import java.io.UnsupportedEncodingException;

public class ExampleViewVoiceCallLegs {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify your access key and a call ID : java -jar <this jar file> test_accesskey");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add service to client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Listing all voice calls
            for (VoiceCallData voiceCall : messageBirdClient.listAllVoiceCalls(null, null).getData()) {
                System.out.printf("Call %s\n", voiceCall.getId());
                System.out.printf("\tstatus: %s\n", voiceCall.getStatus());
                System.out.println("\tlegs:");
                //Viewing call leg by call id
                final VoiceCallLegResponse voiceCallLegResponse =
                        messageBirdClient.viewCallLegsByCallId(voiceCall.getId(), null, null);
                //Display voice call leg response object
                for (VoiceCallLeg callLeg : voiceCallLegResponse.getData()) {
                    System.out.printf("\t\t%s -> %s, %s [%s]\n", callLeg.source, callLeg.destination, callLeg.direction, callLeg.status);
                }
            }

        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
    }
}
