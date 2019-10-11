import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleDownloadTranscription {
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Please specify your access key and call id and leg id and recording id and transcription Id and basePath(optional) example :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938" +
                    " e8077d803532c0b5937c639b60216938 /users/{user}/test");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting a transcription file");
            final String callId = args[1];
            final String legId = args[2];
            final String recordingId = args[3];
            final String transcriptionId = args[4];
            String basePath = null;
            if (args.length > 5) {
                basePath = args[5];
            }
            //Sending call id and leg id and recording and transcription id parameters to client
            final String filePath = messageBirdClient.downloadTranscription(callId, legId, recordingId, transcriptionId, basePath);
            if (filePath != null) {
                System.out.println("Transcription file is downloaded to "+filePath);
            }

        } catch (GeneralException | NotFoundException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
