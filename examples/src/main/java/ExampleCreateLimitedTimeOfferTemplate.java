import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleCreateLimitedTimeOfferTemplate {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key and a template name and WABA ID example : java -jar <this jar file> test_accesskey(Required) templateName(Required) wabaID(required)");
            return;
        }

        final String accessKey = args[0];
        final String templateName = args[1];
        final String wabaID = args[2];

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(accessKey);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        // header
        final HSMComponent headerComponent = new HSMComponent();
        final HSMExample headerExample = new HSMExample();
        headerExample.setHeader_url(Arrays.asList("https://images.freeimages.com/images/small-previews/c5a/colourful-paper-rip-1-1195879.jpg"));

        headerComponent.setType(HSMComponentType.HEADER);
        headerComponent.setFormat(HSMComponentFormat.IMAGE);
        headerComponent.setExample(headerExample);

        // limited time offer
        final HSMComponent ltoComponent = new HSMComponent();
        ltoComponent.setType(HSMComponentType.LIMITED_TIME_OFFER);
        ltoComponent.setText("Expiring offer!");
        ltoComponent.setHasExpiration(true);

        // body
        final HSMComponent bodyComponent = new HSMComponent();
        final HSMExample bodyExample = new HSMExample();
        final List<List<String>> bodyText = new ArrayList<>();
        bodyText.add(Arrays.asList("John", "CARIBE25"));
        bodyExample.setBody_text(bodyText);

        bodyComponent.setType(HSMComponentType.BODY);
        bodyComponent.setText("Good news, {{1}}! Use code {{2}} to get 0% off all packages!");
        bodyComponent.setExample(bodyExample);

        // buttons
        final HSMComponent buttonComponent = new HSMComponent();
        final List<HSMComponentButton> buttons = new ArrayList<>();

        final HSMComponentButton buttonCopyCode = new HSMComponentButton();
        buttonCopyCode.setType(HSMComponentButtonType.COPY_CODE);
        buttonCopyCode.setExample(Arrays.asList("CARIBE25"));

        final HSMComponentButton buttonBookNow = new HSMComponentButton();
        buttonBookNow.setType(HSMComponentButtonType.URL);
        buttonBookNow.setText("Book now!");
        buttonBookNow.setUrl("https://www.bird.com?code={{1}}");
        buttonBookNow.setExample(Arrays.asList("https://www.bird.com?code=CARIBE25"));

        buttons.addAll(Arrays.asList(buttonCopyCode, buttonBookNow));
        buttonComponent.setType(HSMComponentType.BUTTONS);
        buttonComponent.setButtons(buttons);

        // set components
        final Template template = new Template();
        final List<HSMComponent> components = new ArrayList<>();
        components.addAll(Arrays.asList(headerComponent, ltoComponent, bodyComponent, buttonComponent));

        template.setName(templateName);
        template.setLanguage("en_US");
        template.setWABAID(wabaID);
        template.setComponents(components);
        template.setCategory(HSMCategory.MARKETING);

        try {
            TemplateResponse response = messageBirdClient.createWhatsAppTemplate(template);
            System.out.println(response.toString());
        } catch (UnauthorizedException e) {
            System.err.println("Authorization failed. Please check your access key: " + e.getMessage());
        } catch (GeneralException e) {
            System.err.println("An error occurred while sending the message: " + e.getMessage());
        }
    }

}
