package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.voicecalls.*;

import org.junit.*;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import java.util.*;

/*
 * Class used for covering all the CallFlows functionality with tests
 * The purpose is solely on the individual CallFlow
 * and not on the associated callFlows to voiceCall
 */
public class VoiceCallFlowTest {

    private static MessageBirdServiceImpl messageBirdService;
    private static MessageBirdClient messageBirdClient;

    /**
     * VoiceCallFlow object to operate on during tests.
     */
    private static VoiceCallFlow voiceCallFlow;
    private static VoiceCallFlow voiceCallFlowFixture;

    @BeforeClass
    public static void setUpClass() throws UnauthorizedException, GeneralException {
        String accessKey = System.getProperty("messageBirdAccessKey");
        messageBirdService = new MessageBirdServiceImpl(accessKey);
        messageBirdClient = new MessageBirdClient(messageBirdService);
        voiceCallFlowFixture = new VoiceCallFlow();
        voiceCallFlowFixture.setTitle("Test Title");
        voiceCallFlowFixture.setDefaultCall(false);
        voiceCallFlowFixture.setDefaultWebRtc(true);
        voiceCallFlowFixture.setRecord(true);
        voiceCallFlowFixture.setSteps(Collections.singletonList(TestUtil.createVoiceStep()));
        VoiceCallFlowResponse voiceCallFlowResponse = createVoiceCallFlow(voiceCallFlowFixture);
        voiceCallFlow = voiceCallFlowResponse.getData().get(0);

    }

    /*
     * static method used for creating a VoiceCallFlow from the fixture defined at class level
     */
    private static VoiceCallFlowResponse createVoiceCallFlow(VoiceCallFlow voiceCallFlowFixture) throws UnauthorizedException, GeneralException {
        VoiceCallFlowRequest voiceCallFlowRequest = new VoiceCallFlowRequest();
        voiceCallFlowRequest.setTitle(voiceCallFlowFixture.getTitle());
        voiceCallFlowRequest.setRecord(voiceCallFlowFixture.isRecord());
        voiceCallFlowRequest.setDefaultCall(voiceCallFlowFixture.isDefaultCall());
        voiceCallFlowRequest.setSteps(voiceCallFlowFixture.getSteps());

        return messageBirdClient.sendVoiceCallFlow(voiceCallFlowRequest);
    }

    /*
     * For this test we are checking if the CallFlow inserted in the setup is visible in the list
     * This way we test the Create SDK and the List method in one test
     */
    @Test
    public void testCreateAndList() throws UnauthorizedException, GeneralException {
        VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(0, 0);
        VoiceCallFlow foundVoiceCall = null;
        Iterator<VoiceCallFlow> items = voiceCallFlowList.getItems().iterator();
        while (items.hasNext()) {
            VoiceCallFlow nextItem = items.next();
            if (nextItem.getId().equals(voiceCallFlow.getId())) {
                foundVoiceCall = nextItem;
            }
        }
        assertNotNull(foundVoiceCall);
        assertEquals(foundVoiceCall.getTitle(), this.voiceCallFlowFixture.getTitle());
        assertEquals(foundVoiceCall.isDefaultCall(), this.voiceCallFlowFixture.isDefaultCall());
        assertEquals(foundVoiceCall.getSteps().size(), this.voiceCallFlowFixture.getSteps().size());
    }

    /*
     * In this test we are making use of the previous fixture and object defined
     */
    @Test
    public void testUpdate() throws UnauthorizedException, GeneralException {

    }

    @Test
    public void testView() throws UnauthorizedException, GeneralException {

    }

    /*
     * The purpose of this method is to create various scenarios in regards
     * to creating steps
     */
    public void testCreateSteps() {
        // null steps
        // duplicate steps
        // test Media field - string
        // test media field - array
        // test 2 Steps
        // test 3 options for a step
        // ensure each type of option is visible
    }

    /*
     * The purpose of this method is to update stepOptions and stepActions
     * with various values and ensure that these values become visible
     */
    public void testUpdateSteps() {
        // test update for all fields
        // ensure the values have been update for each field
        // convert media field from string to array
        // update all elements and ensure they are visible
    }

    /**
     * This method tests the create and delete at the same time. In case the delete
     * fails, then the fixture inserted in setup() will still be avaialble
     * on the environment and will require a separate removal
     */
    @Test
    public void testCreateAndDelete()
    throws UnauthorizedException, GeneralException, NotFoundException {
        VoiceCallFlowResponse voiceCallFlowResponse = createVoiceCallFlow(voiceCallFlowFixture);
        String id = voiceCallFlowResponse.getData().get(0).getId();
        messageBirdClient.deleteVoiceCallFlow(
            id
        );
        VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(0, 0);
        boolean isVoiceCallFound = false;
        Iterator<VoiceCallFlow> items = voiceCallFlowList.getItems().iterator();
        while (items.hasNext()) {
            VoiceCallFlow nextItem = items.next();
            if (nextItem.getId().equals(id)) {
                isVoiceCallFound = true;
            }
        }
        assertEquals(false, isVoiceCallFound);
    }

    @AfterClass
    public static void tearDown() throws UnauthorizedException, GeneralException, NotFoundException {
        messageBirdClient.deleteVoiceCallFlow(voiceCallFlow.getId());
    }
}
