import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateList;

/**
 * List templates
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates">List templates</a>
 * @author ssk910
 */
public class ExampleListTemplates {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please specify your access key example : java -jar <this jar file> test_accesskey");
      return;
    }

    // First create your service object
    final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

    // Add the service to the client
    final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

    try {
      System.out.println("Retrieving WhatsApp Template list");
      final TemplateList templateList = messageBirdClient.listWhatsAppTemplates();
      System.out.println(templateList.toString());
    } catch (GeneralException | UnauthorizedException exception) {
      exception.printStackTrace();
    }
  }
}
