import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateResponse;
import java.util.List;

/**
 * List templates by name and WABA ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates-by-name">List templates by name and WABA ID</a>
 * @author ssk910
 */
public class ExampleListTemplatesByNameAndWABAID {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, template name and WABA ID example : java -jar <this jar file> test_accesskey \"My template name\" \"WABA ID\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        // template name from input
        final String templateName = args[1];

        // WABA ID from input
        final String wabaID = args[2];

        try {
            System.out.println("Retrieving WhatsApp Template list by name '" + templateName + "' and WABA ID '" + wabaID + "'");
            final List<TemplateResponse> templateList = messageBirdClient.getWhatsAppTemplatesBy(templateName, wabaID, null);
            System.out.println(templateList.toString());
        } catch (GeneralException | UnauthorizedException | NotFoundException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}