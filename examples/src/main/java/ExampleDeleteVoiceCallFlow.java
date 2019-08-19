import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.exceptions.NotFoundException;

public class ExampleDeleteVoiceCallFlow {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and a voice call flow ID");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            //Deleting voice call by id
            System.out.println("Deleting a Voice Call Flow");
            messageBirdClient.deleteVoiceCallFlow(args[1]);
            System.out.println("Voice call flow deleted ");

        } catch (GeneralException | NotFoundException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}