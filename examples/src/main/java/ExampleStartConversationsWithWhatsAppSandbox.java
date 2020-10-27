import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.*;

public class ExampleStartConversationsWithWhatsAppSandbox {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please at least specify your access key, the channel id and destination address.\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) channel_id(Required) to(Required)");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        List<MessageBirdClient.Feature> features = new ArrayList<>();
        features.add(MessageBirdClient.Feature.ENABLE_CONVERSATION_API_WHATSAPP_SANDBOX);

        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr, features); //Create client with WhatsApp Sandbox enabled

        ConversationContent conversationContent = new ConversationContent();
        conversationContent.setText("Hello world from java sdk");

        // Optional source parameter, that identifies the actor making the request.
        Map<String, Object> source = new HashMap<>();
        source.put("agentId", "abc123");

        ConversationStartRequest request = ConversationStartRequest.builder()
                .to(args[2])
                .type(ConversationContentType.TEXT)
                .content(conversationContent)
                .channelId(args[1])
                .source(source)
                .build();

        try {
            Conversation conversation = messageBirdClient.startConversation(request);
            // assertEquals("convid", conversation.getId());
            System.out.println(conversation.getId());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
