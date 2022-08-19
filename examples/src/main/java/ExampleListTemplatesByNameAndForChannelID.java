import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateResponse;
import java.util.List;

/**
 * List templates by name and for channel ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#list-templates-by-name">List templates by name and for channel ID</a>
 * @author ssk910
 */
public class ExampleListTemplatesByNameAndForChannelID {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please specify your access key, template name and channel ID example : java -jar <this jar file> test_accesskey \"My template name\" \"Channel ID\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        // template name from input
        final String templateName = args[1];

        // Channel ID from input
        final String channelID = args[2];

        // Will return templates only if the channel belongs to the same WABA that the templates belongs to.
        try {
            System.out.println("Retrieving WhatsApp Template list by name '" + templateName + "' and channel ID '" + channelID + "'");
            final List<TemplateResponse> templateList = messageBirdClient.getWhatsAppTemplatesBy(templateName, null, channelID);
            System.out.println(templateList.toString());
        } catch (GeneralException | UnauthorizedException | NotFoundException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}