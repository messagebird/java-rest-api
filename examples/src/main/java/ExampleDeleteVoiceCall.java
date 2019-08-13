import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.exceptions.NotFoundException;

public class ExampleDeleteVoiceCall {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a call ID : java -jar <this jar file> test_accesskey e8077d803532c0b5937c639b60216938");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Deleting voice call by id
            System.out.println("Deleting voice call");
            messageBirdClient.deleteVoiceCall(args[1]);
            System.out.println("Voice call [" + args[1] + "] deleted.");

        } catch (GeneralException | NotFoundException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}