import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateResponse;

/**
 * Fetch template by name and language
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#fetch-template-by-name-and-language">Fetch template by name and language</a>
 * @author ssk910
 */
public class ExampleFetchTemplateByNameAndLanguage {

  public static void main(String[] args) {
    if (args.length < 3) {
      System.out.println("Please specify your access key and a template name example : java -jar <this jar file> test_accesskey \"My template name\" \"Template language\"");
      return;
    }

    // First create your service object
    final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

    // template name and langugae from input
    final String templateName = args[1];
    final String language = args[2];

    try {
      System.out.println("Fetching WhatsApp Template list by {name: " + templateName + ", language: " + language + "}");
      final TemplateResponse template = messageBirdClient.fetchWhatsAppTemplateBy(templateName, language);
      System.out.println(template.toString());
    } catch (GeneralException | UnauthorizedException | NotFoundException exception) {
      exception.printStackTrace();
    }
  }
}
