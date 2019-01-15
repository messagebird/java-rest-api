import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.voicecalls.TranscriptionResponse;

public class ExampleSendTranscription {

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Please specify your access key, call ID, leg ID, recording ID and a language :" +
                    " java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 e8077d803532c0b5937c639b60216938 \"nl-NL\"");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Creating new transcription..");
            TranscriptionResponse transcription = messageBirdClient.createTranscription(args[1], args[2], args[3], args[4]);
            System.out.println(transcription.getData().get(0).toString());
        } catch (GeneralException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }
}
