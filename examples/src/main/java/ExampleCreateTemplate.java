import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.HSMCategory;
import com.messagebird.objects.integrations.HSMComponent;
import com.messagebird.objects.integrations.HSMComponentButton;
import com.messagebird.objects.integrations.HSMComponentButtonType;
import com.messagebird.objects.integrations.HSMComponentFormat;
import com.messagebird.objects.integrations.HSMComponentType;
import com.messagebird.objects.integrations.HSMExample;
import com.messagebird.objects.integrations.Template;
import com.messagebird.objects.integrations.TemplateResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create template.
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#create-template">Doc - Create template</a>
 * @author ssk910
 */
public class ExampleCreateTemplate {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Please specify your access key and a template name example : java -jar <this jar file> test_accesskey \"My template name\"");
      return;
    }

    // First create your service object
    final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

    /* header */
    final HSMComponent headerComponent = new HSMComponent();
    final HSMExample headerExample = new HSMExample();
    headerExample.setHeader_url(Arrays.asList("https://images.freeimages.com/images/small-previews/c5a/colourful-paper-rip-1-1195879.jpg"));

    headerComponent.setType(HSMComponentType.HEADER);
    headerComponent.setFormat(HSMComponentFormat.IMAGE);
    headerComponent.setExample(headerExample);

    /* body */
    final HSMComponent bodyComponent = new HSMComponent();
    final HSMExample bodyExample = new HSMExample();
    final List<List<String>> bodyText = new ArrayList<>();
    bodyText.add(Arrays.asList("John"));
    bodyText.add(Arrays.asList("Anna"));
    bodyExample.setBody_text(bodyText);

    bodyComponent.setType(HSMComponentType.BODY);
    bodyComponent.setText("Hey {{1}}! This is a sample template from Java.");
    bodyComponent.setExample(bodyExample);

    /* footer */
    final HSMComponent footerComponent = new HSMComponent();
    footerComponent.setType(HSMComponentType.FOOTER);
    footerComponent.setText("This is a sample footer");

    /* button */
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

    /* set components */
    final Template template = new Template();
    final List<HSMComponent> components = new ArrayList<>();
    components.add(headerComponent);
    components.add(bodyComponent);
    components.add(footerComponent);
    components.add(buttonComponent);

    template.setName(args[1]);
    template.setLanguage("en_US");
    template.setComponents(components);
    template.setCategory(HSMCategory.ACCOUNT_UPDATE);

    try {
      TemplateResponse response = messageBirdClient.createWhatsAppTemplate(template);
      System.out.println(response.toString());
    } catch (GeneralException | UnauthorizedException exception) {
      exception.printStackTrace();
    }
  }
}
