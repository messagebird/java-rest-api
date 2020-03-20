import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

public class ExampleDeleteRecording {
    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Please specify your access key and a call_id, leg_id, and recording_id to delete: java -jar <this jar file> <test_accesskey> <call_id> <leg_id> <recording_id>" );
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Deleting message by id
            System.out.println("Delete recording:");
            messageBirdClient.deleteRecording(args[1],args[2],args[3]);
            System.out.println("Recording ID ["+args[3]+"] deleted.");

        } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
