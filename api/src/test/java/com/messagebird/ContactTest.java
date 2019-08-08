package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import org.junit.*;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @deprecated - This is an integration test, not a unit test and it should
 * be refactored to use mocks instead of LIVE API
 */
public class ContactTest {

    private static MessageBirdServiceImpl messageBirdService;
    private static MessageBirdClient messageBirdClient;
    private static final String CONTACTPATH = "/contacts";

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

    /**
     * Contacts are a bit different when it comes to testing from other
     * resources/endpoints: the other tests always operate on the same MSISDN, but
     * that would cause problems here as we can not have multiple contacts with
     * equal MSISDNs. We therefore generate a random MSIDSN before running the
     * tests here, and clean up by deleting it afterwards.
     * We can't simply delete the contact first if it exists, because we do not
     * know its ID.
     */
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
        final ContactList contactResponseList = TestUtil.createContactList();

        MessageBirdService messageBirdServiceMock = mock(MessageBirdService.class);
        MessageBirdClient messageBirdClientInjectMock = new MessageBirdClient(messageBirdServiceMock);

        when(messageBirdServiceMock.requestList(Mockito.eq(CONTACTPATH), Mockito.eq(0), Mockito.eq(20),
                Mockito.eq(ContactList.class)))
                .thenReturn(contactResponseList);

        final ContactList response = messageBirdClientInjectMock.listContacts();
        verify(messageBirdServiceMock, times(1))
                .requestList(Mockito.eq(CONTACTPATH), Mockito.eq(0), Mockito.eq(20),
                        Mockito.eq(ContactList.class));
        assertReflectionEquals(response.getItems().get(0),contactResponseList.getItems().get(0));
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
