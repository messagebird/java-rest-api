import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

/**
 * List templates by name
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates-by-name">List templates by name</a>
 * @author ssk910
 */
public class ExampleDeleteTemplatesByName {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Please specify your access key and a template name example : java -jar <this jar file> test_accesskey \"My template name\"");
      return;
    }

    // First create your service object
    final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

    // template name from input
    final String templateName = args[1];

    try {
      System.out.println("Deleting WhatsApp Templates by name : " + templateName);
      messageBirdClient.deleteTemplatesBy(templateName);
      System.out.println("Template [" + templateName + "] deleted.");
    } catch (GeneralException | UnauthorizedException | NotFoundException exception) {
      exception.printStackTrace();
    }
  }
}
