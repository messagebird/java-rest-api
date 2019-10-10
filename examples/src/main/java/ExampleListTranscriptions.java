import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.Transcription;
import com.messagebird.objects.voicecalls.TranscriptionResponse;

public class ExampleListTranscriptions {
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Please specify your access key and call id and leg id and recording id and page and page size example :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 1 10");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting transcriptions");
            final String callId = args[1];
            final String legId = args[2];
            final String recordingId = args[3];
            final Integer page = Integer.valueOf(args[4]);
            final Integer pageSize = Integer.valueOf(args[5]);
            TranscriptionResponse transcriptions = messageBirdClient.listTranscriptions(callId, legId, recordingId, page, pageSize);
            if(transcriptions.getData() == null) {
                System.out.println("no transcriptions found");
                return;
            }
            for (Transcription transcription: transcriptions.getData()) {
                System.out.println(transcription.toString());
                System.out.println();
            }

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
