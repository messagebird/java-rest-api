package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.*;
import com.messagebird.objects.voicecalls.*;
import com.messagebird.util.Resources;

import org.junit.*;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import java.util.*;

/*
 * Class used for covering all the CallFlows functionality with tests
 * The purpose is solely on the individual CallFlow
 * and not on the associated callFlows to voiceCall
 */
public class VoiceCallFlowTest {

    private static MessageBirdServiceImpl messageBirdService;
    private static MessageBirdClient messageBirdClient;
    private static String NOT_FOUND_ERROR = "{\"data\":null,\"errors\":[{\"message\":\"No call flow found for ID `1`.\",\"code\":13}]}";
    
    @BeforeClass
    public static void setUpClass() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"));
    }

    /*
     * We define a fixture and we test against the fixture all the setters and getters
     * as well as the transformation of the JSON retrieved
     */
    @Test
    public void testCreate() throws GeneralException, UnauthorizedException {
        VoiceCallFlowRequest voiceCallFlowRequest = TestUtil.createVoiceCallFlowRequest();
        String responseFixture = Resources.readResourceText("/fixtures/call_flows_post.json");

        MessageBirdService messageBirdService = SpyService
            .expects("POST", "call-flows", voiceCallFlowRequest)
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlow voiceCallFlow = messageBirdClient.sendVoiceCallFlow(voiceCallFlowRequest).getData().get(0);
        this.testVoiceCallFlowAgainstFixture(voiceCallFlow);
    }

    @Test
    public void testView() throws GeneralException, UnauthorizedException, NotFoundException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flow_view.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows/e781a76f-14ad-45b0-8490-409300244e20")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlow voiceCallFlow = messageBirdClient
            .viewVoiceCallFlow("e781a76f-14ad-45b0-8490-409300244e20")
            .getData()
            .get(0);
        this.testVoiceCallFlowAgainstFixture(voiceCallFlow);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testViewShouldThrowInvalidArgumentException() throws NotFoundException, GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flow_view.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows/e781a76f-14ad-45b0-8490-409300244e20")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient
            .viewVoiceCallFlow(null);
    }

    @Test
    public void testUpdate() throws GeneralException, UnauthorizedException, NotFoundException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flow_update_response.json");

        VoiceCallFlowRequest voiceCallFlowRequest = new VoiceCallFlowRequest("e781a76f-14ad-45b0-8490-409300244e20");
        voiceCallFlowRequest.setTitle("Forward call to 316123456782");
        voiceCallFlowRequest.setDefaultCall(true);
        voiceCallFlowRequest.setRecord(true);
        voiceCallFlowRequest.setSteps(
            Collections.singletonList(
                TestUtil.createVoiceStep()
            )
        );

        MessageBirdService messageBirdService = SpyService
            .expects("PUT", "call-flows/e781a76f-14ad-45b0-8490-409300244e20", voiceCallFlowRequest)
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        
        VoiceCallFlow voiceCallFlow = messageBirdClient
            .updateVoiceCallFlow("e781a76f-14ad-45b0-8490-409300244e20", voiceCallFlowRequest)
            .getData()
            .get(0);
        this.testVoiceCallFlowAgainstFixture(voiceCallFlow);
    }

    @Test (expected = GeneralException.class)
    public void testUpdateGeneralException() throws GeneralException, UnauthorizedException {
        VoiceCallFlowRequest voiceCallFlowRequest = new VoiceCallFlowRequest("123");
        MessageBirdService messageBirdService = SpyService
            .expects("PUT", "call-flows/123", voiceCallFlowRequest)
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(NOT_FOUND_ERROR, HTTP_NOT_FOUND));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient
            .updateVoiceCallFlow("123", voiceCallFlowRequest);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUpdateIllegalArgumentException() throws GeneralException, UnauthorizedException {
        VoiceCallFlowRequest voiceCallFlowRequest = new VoiceCallFlowRequest("123");
        MessageBirdService messageBirdService = SpyService
            .expects("PUT", "call-flows/123", voiceCallFlowRequest)
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(NOT_FOUND_ERROR, HTTP_NOT_FOUND));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient
            .updateVoiceCallFlow(null, voiceCallFlowRequest);
    }

    @Test (expected = NotFoundException.class)
    public void testViewNotFoundException() throws NotFoundException, GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flow_view.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows/e781a76f-14ad-45b0-8490-409300244e20")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(NOT_FOUND_ERROR, HTTP_NOT_FOUND));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlow voiceCallFlow = messageBirdClient
            .viewVoiceCallFlow("e781a76f-14ad-45b0-8490-409300244e20")
            .getData()
            .get(0);

    }

    @Test
    public void testList() throws GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flows_list.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows?offset=0&limit=0")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(0, 0);
        assertEquals((int) voiceCallFlowList.getItems().size(), 1);
        assertEquals((int) voiceCallFlowList.getTotalCount(), 10);
        assertEquals((int) voiceCallFlowList.getPageCount(), 3);
        assertEquals((int) voiceCallFlowList.getCurrentPage(), 2);
        assertEquals((int) voiceCallFlowList.getPerPage(), 12);
        VoiceCallFlow voiceCallFlow = voiceCallFlowList.getItems().get(0);
        this.testVoiceCallFlowAgainstFixture(voiceCallFlow);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testListShouldThrowInvalidArgumentException()
        throws GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flows_list.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows?offset=0&limit=0")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(-1, 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testListShouldThrowInvalidArgumentExceptionForLimit()
        throws GeneralException, UnauthorizedException {
        String responseFixture = Resources.readResourceText("/fixtures/call_flows_list.json");
        MessageBirdService messageBirdService = SpyService
            .expects("GET", "call-flows?offset=0&limit=0")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse(responseFixture));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        VoiceCallFlowList voiceCallFlowList = messageBirdClient.listVoiceCallFlows(0, -1);
    }   

    @Test
    public void testDelete() throws GeneralException, UnauthorizedException, NotFoundException {
        MessageBirdService messageBirdService = SpyService
            .expects("DELETE", "call-flows/123abc")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse("", HTTP_NO_CONTENT));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient.deleteVoiceCallFlow("123abc");
    }

    @Test (expected = NotFoundException.class)
    public void testDeleteNotFoundException() throws NotFoundException, GeneralException, UnauthorizedException {
        // test not found exception
        MessageBirdService messageBirdService = SpyService
            .expects("DELETE", "call-flows/123abc")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse("", HTTP_NOT_FOUND));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient.deleteVoiceCallFlow("123abc");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteIllegalArgumentException() throws NotFoundException, GeneralException, UnauthorizedException {
        // test not found exception
        MessageBirdService messageBirdService = SpyService
            .expects("DELETE", "call-flows/123abc")
            .withVoiceCallAPIBaseURL()
            .andReturns(new APIResponse("", HTTP_NOT_FOUND));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
        messageBirdClient.deleteVoiceCallFlow(null);
    }

    /**
     * In order to reuse this method for further tests you need to make sure that the fixtures
     * match the date in this test. See call_flows_post.json
     */
    private void testVoiceCallFlowAgainstFixture(VoiceCallFlow voiceCallFlow) {
        assertEquals(voiceCallFlow.getId(), "e781a76f-14ad-45b0-8490-409300244e20");
        assertEquals(voiceCallFlow.getTitle(), "Forward call to 31612345678");
        assertEquals(voiceCallFlow.isRecord(), true);
        assertEquals(voiceCallFlow.isDefaultCall(), false);
        assertEquals(voiceCallFlow.getCreatedAt().toString(), "Tue Aug 06 16:13:06 CEST 2019");
        assertEquals(voiceCallFlow.getUpdatedAt().toString(), "Tue Aug 06 16:13:06 CEST 2019");
        assertEquals(voiceCallFlow.getSteps().size(), 1);
        VoiceStep voiceStep = voiceCallFlow.getSteps().get(0);
        assertEquals(voiceStep.getId(), "a8e44a38-b935-482f-b17f-ed3472c6292c");
        assertEquals(voiceStep.getAction(), "transfer");
        VoiceStepOption voiceStepOption = voiceStep.getOptions();
        assertEquals(voiceStepOption.getDestination(), "31612345678");
        assertEquals(voiceStepOption.getPayload(), "Test payload");
        assertEquals(voiceStepOption.getLanguage(), "en-GB");
        assertEquals(voiceStepOption.getVoice(), "male");
        assertEquals(voiceStepOption.getRepeat(), 1);
        assertEquals(voiceStepOption.getMedia(), "test.mp3");
        assertEquals(voiceStepOption.getFinishOnKey(), "1");
        assertEquals(voiceStepOption.getTranscribeLanguage(), "en-GB");
        assertEquals(voiceStepOption.getRecord(), "both");
        assertEquals(voiceStepOption.getUrl(), "http://");
        assertEquals(voiceStepOption.getIfMachine(), "ifMachine");
        assertEquals(voiceStepOption.getMachineTimeout(), 200);
        assertEquals(voiceStepOption.getOnFinish(), "http://");
        assertEquals(voiceStepOption.getLength(), 1);
        assertEquals(voiceStepOption.getTimeout(), 3);
        assertEquals(voiceStepOption.getMaxLength(), 2);
        assertEquals(voiceStepOption.isTranscribe(), false);
    }
}
