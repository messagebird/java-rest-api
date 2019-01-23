import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.TranscriptionResponse;

public class ExampleViewTranscription {

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Please specify your access key, call ID, leg ID, recording ID, page, page size :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting transcription list");
            final String callId = args[1];
            final String legId = args[2];
            final String recordingId = args[3];
            final int page = Integer.valueOf(args[4]);
            final int pageSize = Integer.valueOf(args[5]);
            // Sending call ID, leg ID, recording ID, page, page size parameters to client
            final TranscriptionResponse responseList = messageBirdClient.viewTranscription(callId, legId, recordingId, page, pageSize) ;
            //Display transcription response
            System.out.println(responseList.toString());

        } catch (GeneralException | UnauthorizedException exceptions) {
            exceptions.printStackTrace();
        }

    }
}
