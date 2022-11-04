import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.Collections;

public class ExampleConversationSendHSMTemplateWithButtons {

    // Reference Example: https://developers.messagebird.com/quickstarts/whatsapp/send-message-with-buttons/
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

        ConversationContent conversationContent = new ConversationContent();
        ConversationContentHsm conversationContentHsm = new ConversationContentHsm();
        conversationContentHsm.setNamespace("20332cd4_f095_b080_d255_35677159aaff");
        conversationContentHsm.setTemplateName("33172012024_ship_img_but_1");
        ConversationHsmLanguage language = new ConversationHsmLanguage();
        language.setCode("en");
        conversationContentHsm.setLanguage(language);

        //Define component with button
        MessageComponent messageButtonComponent = new MessageComponent();
        messageButtonComponent.setType(MessageComponentType.BUTTON);
        messageButtonComponent.setSub_type("url");
        MessageParam textParam = new MessageParam();
        textParam.setType(TemplateMediaType.TEXT);
        textParam.setText("23493282245");

        messageButtonComponent.setParameters(Collections.singletonList(textParam));
        conversationContentHsm.setComponents(Collections.singletonList(messageButtonComponent));
        conversationContent.setHsm(conversationContentHsm);
        ConversationSendRequest request = new ConversationSendRequest(
                args[2],
                ConversationContentType.HSM,
                conversationContent,
                args[1],
                "",
                null,
                null,
                null);

        try {
            ConversationSendResponse sendResponse = messageBirdClient.sendMessage(request);
            System.out.println(sendResponse.toString());

        } catch (GeneralException | UnauthorizedException exception) {
            exception.printStackTrace();
        }
    }
}
