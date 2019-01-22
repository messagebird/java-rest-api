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
            //Creating new transcription
            System.out.println("Creating new transcription..");
            final String callId = args[1];
            final String legId = args[2];
            final String recordingId = args[3];
            //Language should be in allowed languages list, can check on developer api
            final String language = args[4];
            final TranscriptionResponse transcription = messageBirdClient.createTranscription(callId, legId, recordingId, language);
            if (transcription.getData() == null) {
                //Data is not present then return exception
                throw new NullPointerException("Transcription response has no data");
            }

            //Display data
            System.out.println(transcription.getData().get(0).toString());
        } catch (GeneralException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }
}
