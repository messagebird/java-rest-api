import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleConversationSendHSMCopyCodeTemplate {

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Please at least specify your access key, the channel id and destination address.\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) channel_id(Required) from(Required) destination(Required) templateName(Required) namespace(Required) couponCodeInput(Required)");
            return;
        }

        final String accessKey = args[0];
        final String from = args[1];
        final String destination = args[2];
        final String templateName = args[3];
        final String namespace = args[4];
        final String couponCodeInput = args[5];

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(accessKey);
        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        ConversationContent conversationContent = new ConversationContent();
        ConversationContentHsm conversationContentHsm = new ConversationContentHsm();
        conversationContentHsm.setNamespace(namespace);
        conversationContentHsm.setTemplateName(templateName);
        ConversationHsmLanguage language = new ConversationHsmLanguage();
        language.setCode("en");
        conversationContentHsm.setLanguage(language);
        List<MessageComponent> messageComponents = new ArrayList<>();

        // Add LTO component
        MessageComponent messageCopyCodeComponent = new MessageComponent();
        messageCopyCodeComponent.setType(MessageComponentType.BUTTON);
        messageCopyCodeComponent.setSub_type(MessageComponentType.COPY_CODE.toString());
        List<MessageParam> messageCCParams = new ArrayList<>();

        MessageParam couponCodeParam = new MessageParam();
        couponCodeParam.setType(TemplateMediaType.COUPON_CODE);
        couponCodeParam.setCouponCode(couponCodeInput);
        messageCCParams.add(couponCodeParam);

        messageCopyCodeComponent.setParameters(messageCCParams);

        // Add body component
        MessageComponent messageBodyComponent = new MessageComponent();
        messageBodyComponent.setType(MessageComponentType.BODY);
        List<MessageParam> messageBodyParams = new ArrayList<>();

        MessageParam text = new MessageParam();
        text.setType(TemplateMediaType.TEXT);
        text.setText("Bob");
        messageBodyParams.add(text);

        messageBodyComponent.setParameters(messageBodyParams);

        messageComponents.add(messageCopyCodeComponent);
        messageComponents.add(messageBodyComponent);
        conversationContentHsm.setComponents(messageComponents);
        conversationContent.setHsm(conversationContentHsm);
        ConversationSendRequest request = new ConversationSendRequest(
                destination,
                ConversationContentType.HSM,
                conversationContent,
                from,
                "",
                null,
                null,
                null);

        try {
            System.out.println(request.toString());
            ConversationSendResponse sendResponse = messageBirdClient.sendMessage(request);
            System.out.println(sendResponse.toString());

        } catch (UnauthorizedException e) {
            System.err.println("Authorization failed. Please check your access key: " + e.getMessage());
        } catch (GeneralException e) {
            System.err.println("An error occurred while sending the message: " + e.getMessage());
        }
    }

}
