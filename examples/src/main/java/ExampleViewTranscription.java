import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.TranscriptionResponse;

public class ExampleViewTranscription {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key, call ID, leg ID, recording ID :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Getting transcription list");
            final TranscriptionResponse responseList = messageBirdClient.viewTranscription(args[1], args[2], args[3]);
            System.out.println(responseList.toString());

        } catch (GeneralException | UnauthorizedException exceptions) {
            exceptions.printStackTrace();
        }

    }
}
