package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Contacts are a bit different when it comes to testing from other
 * resources/endpoints: the other tests always operate on the same MSISDN, but
 * that would cause problems here as we can not have multiple contacts with
 * equal MSISDNs. We therefore generate a random MSIDSN before running the
 * tests here, and clean up by deleting it afterwards.
 * We can't simply delete the contact first if it exists, because we do not
 * know its ID.
 */
public class ContactTest {

    private static MessageBirdServiceImpl messageBirdService;
    private static MessageBirdClient messageBirdClient;

    private static String msisdn;

    /**
     * Contact object to operate on during tests.
     */
    private static Contact contact;

    @BeforeClass
    public static void setUpClass() throws UnauthorizedException, GeneralException {
        String accessKey = System.getProperty("messageBirdAccessKey");

        msisdn = generateMsisdn();

        messageBirdService = new MessageBirdServiceImpl(accessKey);
        messageBirdClient = new MessageBirdClient(messageBirdService);

        createContact();
    }

    private static void createContact() throws UnauthorizedException, GeneralException {
        ContactRequest contactRequest = new ContactRequest(msisdn);

        contact = messageBirdClient.sendContact(contactRequest);
    }

    private static String generateMsisdn() {
        final long lower = 31600000000L, upper = 31699999999L;

        long msisdn = lower + (long) (Math.random() * (upper - lower));

        return String.valueOf(msisdn);
    }

    @AfterClass
    public static void tearDown() throws UnauthorizedException, GeneralException, NotFoundException {
        messageBirdClient.deleteContact(contact.getId());
    }

    @Test
    public void testList() throws UnauthorizedException, GeneralException {
        ContactList actual = messageBirdClient.listContacts();

        assertSame(20, actual.getLimit());
        assertSame(0, actual.getOffset());
        assertNotSame(0, actual.getTotalCount());
        assertNotNull(actual.getItems().get(0).getId());
    }

    @Test
    public void testUpdate() throws UnauthorizedException, GeneralException {
        assertNull(contact.getCustomDetails().getCustom3());

        CustomDetails customDetails = new CustomDetails();
        customDetails.setCustom3("Some custom value");

        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setCustomDetails(customDetails);

        Contact actual = messageBirdClient.updateContact(contact.getId(), contactRequest);

        assertEquals("Some custom value", actual.getCustomDetails().getCustom3());
    }

    @Test
    public void testView() throws UnauthorizedException, GeneralException, NotFoundException {
        Contact actual = messageBirdClient.viewContact(contact.getId());

        assertFalse(actual.getId().isEmpty());
        assertEquals(msisdn, actual.getMsisdn());
        assertSame(0, actual.getGroups().getTotalCount());
    }
}
