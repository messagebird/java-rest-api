import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.TemplateResponse;

/**
 * Fetch template by name and language for Channel ID
 *
 * @see <a href="https://developers.messagebird.com/api/integrations/#fetch-template-by-name-and-language">Fetch template by name and language for Channel ID</a>
 * @author ssk910
 */
public class ExampleFetchTemplateByNameAndLanguageForChannelID {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please specify your access key, template name, language and Channel ID example : java -jar <this jar file> test_accesskey \"My template name\" \"Template language\" \"Channel ID\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        // template name, language and the channel ID from input
        final String templateName = args[1];
        final String language = args[2];
        final String channelID = args[3];

        // Will return a template only if the channel belongs to the same WABA that the template belongs to.
        try {
            System.out.println("Fetching WhatsApp Template list by {name: " + templateName + ", language: " + language + ", channelID: " + channelID + "}");
            final TemplateResponse template = messageBirdClient.fetchWhatsAppTemplateBy(templateName, language, null, channelID);
            System.out.println(template.toString());
        } catch (GeneralException | UnauthorizedException | NotFoundException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}