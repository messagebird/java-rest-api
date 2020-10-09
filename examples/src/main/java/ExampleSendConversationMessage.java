import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.ConversationContent;
import com.messagebird.objects.conversations.ConversationContentMedia;
import com.messagebird.objects.conversations.ConversationContentType;
import com.messagebird.objects.conversations.ConversationMessage;
import com.messagebird.objects.conversations.ConversationMessageRequest;

/**
 * Created by olimpias on 24/3/20.
 */
public class ExampleSendConversationMessage {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body example : java -jar <this jar file> test_accesskey 31612345678,3161112233 \"My message to be send\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        ConversationMessageRequest request = new ConversationMessageRequest();
        request.setChannelId(args[1]);
        ConversationContent content = new ConversationContent();
        ConversationContentMedia media =  new ConversationContentMedia("https://example.com/photo.png", "example");
        content.setImage(media);
        request.setContent(content);
        request.setType(ConversationContentType.IMAGE);
        try {
            final ConversationMessage response = messageBirdClient.sendConversationMessage(args[2], request);
            //Display message response
            System.out.println(response.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }
    }
}
