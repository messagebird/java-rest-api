import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleConversationSendCarouselTemplate {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Please at least specify your access key, the channel id and destination address.\n" +
                    "Usage : java -jar <this jar file> test_accesskey(Required) channel_id(Required) to(Required)");
            return;
        }

        //First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);
        //Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        ConversationContent conversationContent = new ConversationContent();
        ConversationContentHsm conversationContentHsm = new ConversationContentHsm();
        conversationContentHsm.setNamespace("c663a566_a4de_492f_86b2_028cbf612345");
        conversationContentHsm.setTemplateName("carousel_template_test_hello");
        ConversationHsmLanguage language = new ConversationHsmLanguage();
        language.setCode("en_US");
        conversationContentHsm.setLanguage(language);



//        card0 components
        List<MessageComponent> card0Components = new ArrayList<>();
//        card0 header
        MessageComponent card0HeaderComponent = new MessageComponent();
        card0HeaderComponent.setType(MessageComponentType.HEADER);
        MessageParam imageParam = new MessageParam();
        imageParam.setType(TemplateMediaType.IMAGE);
        Media media = new Media();
        media.setUrl("https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg");
        imageParam.setImage(media);
        card0HeaderComponent.setParameters(Collections.singletonList(imageParam));
        card0Components.add(card0HeaderComponent);
//        card0 body
        MessageComponent card0BodyComponent = new MessageComponent();
        card0BodyComponent.setType(MessageComponentType.BODY);
        MessageParam textParam = new MessageParam();
        textParam.setType(TemplateMediaType.TEXT);
        textParam.setText("dummy text");
        card0BodyComponent.setParameters(Collections.singletonList(textParam));
        card0Components.add(card0BodyComponent);
//        card0 button
        MessageComponent card0ButtonComponent = new MessageComponent();
        card0ButtonComponent.setType(MessageComponentType.BUTTON);
        card0ButtonComponent.setSub_type("quick_reply");
        card0ButtonComponent.setIndex(0);
        MessageParam buttonParam = new MessageParam();
        buttonParam.setType(TemplateMediaType.PAYLOAD);
        buttonParam.setPayload("dummy button");
        card0ButtonComponent.setParameters(Collections.singletonList(buttonParam));
        card0Components.add(card0ButtonComponent);

//        card0
        MessageComponent card0 = new MessageComponent();
        card0.setType(MessageComponentType.CARD);
        card0.setCard_index(0);
        card0.setComponents(card0Components);


//        cards list
        List<MessageComponent> cards = new ArrayList<>();
        cards.add(card0);

//        carousel component
        MessageComponent carousel = new MessageComponent();
        carousel.setType(MessageComponentType.CAROUSEL);
        carousel.setCards(cards);
//        body component
        MessageComponent body = new MessageComponent();
        body.setType(MessageComponentType.BODY);
        MessageParam bodyParam = new MessageParam();
        bodyParam.setType(TemplateMediaType.TEXT);
        bodyParam.setText("Jackson");
        body.setParameters(Collections.singletonList(bodyParam));

//        set components to message
        List<MessageComponent> messageComponents = new ArrayList<>();
        messageComponents.add(body);
        messageComponents.add(carousel);
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
