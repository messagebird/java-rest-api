import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.ConversationContent;
import com.messagebird.objects.conversations.ConversationContentEmail;
import com.messagebird.objects.conversations.ConversationContentType;
import com.messagebird.objects.conversations.ConversationEmailContent;
import com.messagebird.objects.conversations.ConversationEmailRecipient;
import com.messagebird.objects.conversations.ConversationSendRequest;
import com.messagebird.objects.conversations.ConversationSendResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExampleConversationSendEmailMessage {

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Please at least specify your access key, the channel id and destination address.\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) channel_id(Required) from(Required) to(Required)");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        ConversationEmailRecipient fromRecipient = new ConversationEmailRecipient();
        fromRecipient.setAddress(args[2]);
        ConversationEmailRecipient toRecipient = new ConversationEmailRecipient();
        toRecipient.setAddress(args[3]);
        ConversationEmailContent content = new ConversationEmailContent();
        content.setHtml("<h1>HTML Ipsum Presents</h1>\n" +
                "\n" +
                "<p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. " +
                "<em>Aenean ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget " +
                "tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"#\">Donec non enim</a> in turpis pulvinar facilisis. Ut felis.</p>\n" +
                "\n" +
                "<h2>Header Level 2</h2>");
        ConversationContentEmail emailContent = new ConversationContentEmail();
        emailContent.setContent(content);
        emailContent.setFrom(fromRecipient);
        emailContent.setTo(Arrays.asList(toRecipient));
        emailContent.setSubject("Greetings From Messagebird");
        ConversationContent conversationContent = new ConversationContent();
        conversationContent.setEmail(emailContent);

        // Optional source parameter, that identifies the actor making the request.
        Map<String, Object> source = new HashMap<>();
        source.put("Salesman", "Sir. John Doe");

        ConversationSendRequest request = new ConversationSendRequest(
                args[2],
                ConversationContentType.EMAIL,
                conversationContent,
                args[1],
                "",
                null,
                source,
                null);

        try {
            ConversationSendResponse sendResponse = messageBirdClient.sendMessage(request);
            System.out.println(sendResponse.toString());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
