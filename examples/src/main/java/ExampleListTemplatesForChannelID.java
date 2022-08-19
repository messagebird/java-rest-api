import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateList;

/**
 * List templates for Channel ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates">List templates for Channel ID</a>
 * @author ssk910
 */
public class ExampleListTemplatesForChannelID {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify your access key and channel ID example : java -jar <this jar file> test_accesskey");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Retrieving WhatsApp Template list for a channel");
            final TemplateList templateList = messageBirdClient.listWhatsAppTemplates(0, 25, null, args[1]);
            System.out.println(templateList.toString());
        } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}
