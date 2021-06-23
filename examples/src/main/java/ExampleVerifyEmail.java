import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.Verify;
import com.messagebird.objects.VerifyMessage;
import com.messagebird.objects.VerifyRequest;

/**
 * Created by leandro.pinto on 23/06/21.
 */
public class ExampleVerifyEmail {

    public static void main(String[] args) throws UnauthorizedException, GeneralException, NotFoundException {

        final String ACCESS_KEY = args[0];
        final String METHOD = args[1];

        final MessageBirdService wsr = new MessageBirdServiceImpl(ACCESS_KEY);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        Verify verify = null;

        switch (METHOD) {
        case "send":
            VerifyRequest request = new VerifyRequest("<ADD RECIPIENT>");
            request.setType("email");
            request.setOriginator("<ADD ORIGINATOR>");
            request.setSubject("<ADD SUBJECT>");
            request.setTimeout(300);

            verify = messageBirdClient.sendVerifyToken(request);
            System.out.println(verify.toString());

            break;
        case "verify":
            final String VERIFY_ID = args[2];
            final String TOKEN = args[3];

            verify = messageBirdClient.verifyToken(VERIFY_ID, TOKEN);
            System.out.println(verify.toString());
            break;
        case "view":
            final String MESSAGE_ID = args[2];
            VerifyMessage verifyMessage = messageBirdClient.getVerifyEmailMessage(MESSAGE_ID);
            System.out.println(verifyMessage.toString());
            break;
        }
    }
}
