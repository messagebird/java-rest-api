import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.integrations.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleCreateAuthTemplate {
    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Please specify your access key and a template name and WABA ID example : java -jar <this jar file> test_accesskey \"My template name\" \"WABA ID\"");
            return;
        }

        // First create your service object
        MessageBirdService wsr = new MessageBirdServiceImpl(args[0]);

        // Add the service to the client
        MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        /* body */
        HSMComponent bodyComponent = new HSMComponent();
        bodyComponent.setType(HSMComponentType.BODY);
        bodyComponent.setAddSecurityRecommendation(true);

        /* footer */
        HSMComponent footerComponent = new HSMComponent();
        footerComponent.setType(HSMComponentType.FOOTER);
        footerComponent.setCodeExpirationMinutes(8);

        /* button */
        HSMComponent buttonComponent = new HSMComponent();
        List<HSMComponentButton> buttons = new ArrayList<>();
        HSMComponentButton otpButton = new HSMComponentButton();
        otpButton.setOtpType(HSMOTPButtonType.ONE_TAP);
        otpButton.setText("Copy code");
        otpButton.setAutofillText("Autofill");
        otpButton.setPackageName("com.example.luckyshrub");
        otpButton.setSignatureHash("K8a%2FAINcGX7");

        buttons.add(otpButton);
        buttonComponent.setType(HSMComponentType.BUTTONS);
        buttonComponent.setButtons(buttons);

        /* set components */
        Template template = new Template();
        List<HSMComponent> components = new ArrayList<>();
        components.add(bodyComponent);
        components.add(footerComponent);
        components.add(buttonComponent);

        template.setName(args[1]);
        template.setLanguage("en_US");
        template.setWABAID(args[2]);
        template.setComponents(components);
        template.setCategory(HSMCategory.AUTHENTICATION);

        try {
            TemplateResponse response = messageBirdClient.createWhatsAppTemplate(template);
            System.out.println(response.toString());
        } catch (GeneralException | UnauthorizedException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
}