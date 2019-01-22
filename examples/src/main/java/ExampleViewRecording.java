import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.RecordingResponse;

public class ExampleViewRecording {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key and call id and leg id and recording id example :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting a recording");
            final String callId = args[1];
            final String legId = args[2];
            final String recordingId = args[3];
            //Sending call id and leg id and recording id parameters to client
            final RecordingResponse recording = messageBirdClient.viewRecording(callId, legId, recordingId);
            //Display recording response
            System.out.println(recording.toString());

        } catch (GeneralException | NotFoundException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

    }
}
