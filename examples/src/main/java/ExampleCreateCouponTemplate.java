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
public class ExampleCreateCouponTemplate {

  public static void main(String[] args) {
    if (args.length < 3) {
      System.out.println("Please specify your access key and a template name and WABA ID example : java -jar <this jar file> test_accesskey \"My template name\" \"WABA ID\"");
      return;
    }

    // First create your service object
    final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

    /* header */
    final HSMComponent headerComponent = new HSMComponent();
    headerComponent.setType(HSMComponentType.HEADER);
    headerComponent.setFormat(HSMComponentFormat.TEXT);
    headerComponent.setText("Our Fall Sale is on!");

    /* body */
    final HSMComponent bodyComponent = new HSMComponent();
    final HSMExample bodyExample = new HSMExample();
    final List<List<String>> bodyText = new ArrayList<>();
    bodyText.add(Arrays.asList("25OFF", "25%"));
    bodyExample.setBody_text(bodyText);

    bodyComponent.setType(HSMComponentType.BODY);
    bodyComponent.setText("Shop now through November and use code {{1}} to get {{2}} off of all merchandise!");
    bodyComponent.setExample(bodyExample);


    /* button */
    final HSMComponent buttonComponent = new HSMComponent();
    final List<HSMComponentButton> buttons = new ArrayList<>();
    final HSMComponentButton button = new HSMComponentButton();
    button.setType(HSMComponentButtonType.COPY_CODE);
    button.setExample(Arrays.asList("CODE25"));
    buttons.add(button);
    buttonComponent.setType(HSMComponentType.BUTTONS);
    buttonComponent.setButtons(buttons);

    /* set components */
    final Template template = new Template();
    final List<HSMComponent> components = new ArrayList<>();
    components.add(headerComponent);
    components.add(bodyComponent);
    components.add(buttonComponent);

    template.setName(args[1]);
    template.setLanguage("en_US");
    template.setWABAID(args[2]);
    template.setComponents(components);
    template.setCategory(HSMCategory.MARKETING);
    template.setCtaURLLinkTrackingOptedOut(true);

    try {
      TemplateResponse response = messageBirdClient.createWhatsAppTemplate(template);
      System.out.println(response.toString());
    } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
      exception.printStackTrace();
    }
  }
}
