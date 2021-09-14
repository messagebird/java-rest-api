import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.ConversationContent;
import com.messagebird.objects.conversations.ConversationContentHsm;
import com.messagebird.objects.conversations.ConversationContentType;
import com.messagebird.objects.conversations.ConversationHsmLanguage;
import com.messagebird.objects.conversations.ConversationSendRequest;
import com.messagebird.objects.conversations.ConversationSendResponse;
import com.messagebird.objects.conversations.Media;
import com.messagebird.objects.conversations.MessageComponent;
import com.messagebird.objects.conversations.MessageComponentType;
import com.messagebird.objects.conversations.MessageParam;
import com.messagebird.objects.conversations.TemplateMediaType;
import java.util.ArrayList;
import java.util.List;

public class ExampleConversationSendHSMMediaTemplate {

    // Reference Example: https://developers.messagebird.com/quickstarts/whatsapp/send-media-template-message/
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
        List<MessageComponent> messageComponents = new ArrayList<>();
        //Define header component with image
        MessageComponent messageHeaderComponent = new MessageComponent();
        messageHeaderComponent.setType(MessageComponentType.HEADER);
        MessageParam imageParam = new MessageParam();
        Media media = new Media();
        media.setUrl("https://i.ytimg.com/vi/3fDoOw4lIeU/maxresdefault.jpg");
        imageParam.setImage(media);
        imageParam.setType(TemplateMediaType.IMAGE);
        List<MessageParam> messageHeaderParams = new ArrayList<>();
        messageHeaderParams.add(imageParam);
        messageHeaderComponent.setParameters(messageHeaderParams);
        //Define body component with texts
        MessageComponent messageBodyComponent = new MessageComponent();
        messageBodyComponent.setType(MessageComponentType.BODY);
        List<MessageParam> messageBodyParams = new ArrayList<>();
        messageBodyComponent.setParameters(messageBodyParams);
        MessageParam firstText = new MessageParam();
        firstText.setType(TemplateMediaType.TEXT);
        firstText.setText("John");
        messageBodyParams.add(firstText);

        MessageParam secondText = new MessageParam();
        secondText.setType(TemplateMediaType.TEXT);
        secondText.setText("MB93824");
        messageBodyParams.add(secondText);

        MessageParam thirdText = new MessageParam();
        thirdText.setType(TemplateMediaType.TEXT);
        thirdText.setText("2 days");
        messageBodyParams.add(thirdText);

        MessageParam fourthText = new MessageParam();
        fourthText.setType(TemplateMediaType.TEXT);
        fourthText.setText("MessageBird");
        messageBodyParams.add(fourthText);

        messageComponents.add(messageHeaderComponent);
        messageComponents.add(messageBodyComponent);
        conversationContentHsm.setComponents(messageComponents);
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
