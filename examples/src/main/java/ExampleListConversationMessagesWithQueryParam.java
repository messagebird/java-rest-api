import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.conversations.ConversationMessageList;
import java.util.HashMap;

public class ExampleListConversationMessagesWithQueryParam {
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
      // Get list of conversation messages with query param
      System.out.println("Retrieving message list");
      final ConversationMessageList conversationMessageList = messageBirdClient.listConversationMessagesWithQueryParam(
          new HashMap<String, Object>() {
            {
              put("ids", "9f0b413e79e24d76b01b895381b12a6d,d46054ee0f7245bcbc7ba586878d0ab4");
            }
          });

      // Display conversation Message list
      System.out.println(conversationMessageList.toString());
    } catch (UnauthorizedException | GeneralException | NotFoundException exception) {
      if (exception.getErrors() != null) {
        System.out.println(exception.getErrors().toString());
      }
      exception.printStackTrace();
    }
  }
}
