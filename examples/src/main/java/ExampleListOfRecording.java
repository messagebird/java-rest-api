import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.RecordingResponse;

public class ExampleListOfRecording {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key and call id and leg id example :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938");
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
            //Sending call id and leg id and recording id parameters to client
            final RecordingResponse recordings = messageBirdClient.listRecordings(callId, legId, 0, 0);
            if (recordings.getData() == null) {
                System.out.println("No record data found");
            }
            //Display recording responses
            for(int i = 0; i< recordings.getData().size(); i++) {
                System.out.println(recordings.getData().get(i).toString());
                System.out.println();
            }

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }

    }
}
