import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create template.
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#create-template">Doc - Create template</a>
 * @author AlexL-mb
 */
public class ExampleCreateCarouselTemplate {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key and a template name and WABA ID example : java -jar <this jar file> test_accesskey \"My template name\" \"WABA ID\"");
            return;
        }

        // First create your service object
        MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        /* body */
        HSMComponent bodyComponent = new HSMComponent();
        final HSMExample bodyExample = new HSMExample();
        final List<List<String>> bodyText = new ArrayList<>();
        bodyText.add(Arrays.asList("John"));
        bodyExample.setBody_text(bodyText);
        bodyComponent.setType(HSMComponentType.BODY);
        bodyComponent.setText("Hey {{1}}! This is a sample template from Java.");
        bodyComponent.setExample(bodyExample);

        /* carousel */
        final HSMComponent carouselComponent = new HSMComponent();
        carouselComponent.setType(HSMComponentType.CAROUSEL);

        /* cards */
        final List<HSMComponentCard> cards = new ArrayList<>();
        /* card 0 */
        final HSMComponentCard card = new HSMComponentCard();
        final List<HSMComponent> cardComponents = new ArrayList<>();

        /* card header */
        final HSMComponent headerComponent = new HSMComponent();
        final HSMExample headerExample = new HSMExample();
        headerExample.setHeader_url(Arrays.asList("https://images.freeimages.com/images/small-previews/c5a/colourful-paper-rip-1-1195879.jpg"));

        headerComponent.setType(HSMComponentType.HEADER);
        headerComponent.setFormat(HSMComponentFormat.IMAGE);
        headerComponent.setExample(headerExample);
        cardComponents.add(headerComponent);

        /* card body */
        final HSMComponent cardBodyComponent = new HSMComponent();
        final HSMExample cardBodyExample = new HSMExample();
        final List<List<String>> cardBodyText = new ArrayList<>();
        cardBodyText.add(Arrays.asList("John"));
        cardBodyExample.setBody_text(cardBodyText);

        cardBodyComponent.setType(HSMComponentType.BODY);
        cardBodyComponent.setText("Hey {{1}}! This is a sample template from Java.");
        cardBodyComponent.setExample(cardBodyExample);
        cardComponents.add(cardBodyComponent);

        /* card buttons */
        final HSMComponent buttonComponent = new HSMComponent();
        final List<HSMComponentButton> buttons = new ArrayList<>();
        final HSMComponentButton button = new HSMComponentButton();
        button.setType(HSMComponentButtonType.URL);
        button.setText("Touch it");
        button.setUrl("https://www.messagebird.com");
        button.setExample(Arrays.asList("https://developers.messagebird.com"));
        buttons.add(button);
        buttonComponent.setType(HSMComponentType.BUTTONS);
        buttonComponent.setButtons(buttons);
        cardComponents.add(buttonComponent);

        card.setComponents(cardComponents);

        cards.add(card);

        carouselComponent.setCards(cards);

        /* set components */
        Template template = new Template();
        List<HSMComponent> components = new ArrayList<>();
        components.add(bodyComponent);
        components.add(carouselComponent);

        template.setName(args[1]);
        template.setLanguage("en_US");
        template.setWABAID(args[2]);
        template.setComponents(components);
        template.setCategory(HSMCategory.MARKETING);

        try {
            TemplateResponse response = messageBirdClient.createWhatsAppTemplate(template);
            System.out.println(response.toString());
        } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }

    }
}
