import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;
import java.util.List;

public class ExampleStartConversationsWithWhatsAppSandbox {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please at least specify your access key, the channel id and destination address.\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) channel_id(Required) to(Required)");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr, List.of(MessageBirdClient.Feature.ENABLE_CONVERSATION_API_WHATSAPP_SANDBOX)); //Create client with WhatsApp Sandbox enabled

        ConversationContent conversationContent = new ConversationContent();
        conversationContent.setText("Hello world from java sdk");

        ConversationStartRequest request = new ConversationStartRequest(
                args[2],
                ConversationContentType.TEXT,
                conversationContent,
                args[1]
        );
        try {
            Conversation conversation = messageBirdClient.startConversation(request);
            // assertEquals("convid", conversation.getId());
            System.out.println(conversation.getId());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
