import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateList;

/**
 * List templates by WABA ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates">List templates by WABA ID</a>
 * @author ssk910
 */
public class ExampleListTemplatesByWABAID {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify your access key and WABA ID example : java -jar <this jar file> test_accesskey \"WABA ID\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            System.out.println("Retrieving WhatsApp Template list by WABA");
            final TemplateList templateList = messageBirdClient.listWhatsAppTemplates(0, 25, "your-waba-id", null);
            System.out.println(templateList.toString());
        } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}

/**
 * List templates for Channel ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates">List templates for Channel ID</a>
 * @author ssk910
 */
public class ExampleListTemplatesForChannelID {

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
            System.out.println("Retrieving WhatsApp Template list for a channel");
            final TemplateList templateList = messageBirdClient.listWhatsAppTemplates(0, 25, null, "channel-id");
            System.out.println(templateList.toString());
        } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}
