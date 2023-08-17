package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.OutboundSmsPriceResponse;
import com.messagebird.util.Resources;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class OutboundSmsPricesTest {

    @Test
    public void testGetOutboundSmsPrices() throws GeneralException, UnauthorizedException, NotFoundException {
        String responseFixture = Resources.readResourceText("/fixtures/outbound_sms_prices.json");

        MessageBirdService messageBirdService = SpyService
                .expects("GET", "pricing/sms/outbound")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(responseFixture, 200));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        assertReceivedExpectedResponse(messageBirdClient.getOutboundSmsPrices());
    }

    @Test
    public void testGetOutboundSmsPricesSmppUsername() throws GeneralException, UnauthorizedException, NotFoundException {
        String responseFixture = Resources.readResourceText("/fixtures/outbound_sms_prices.json");

        MessageBirdService messageBirdService = SpyService
                .expects("GET", "pricing/sms/outbound/smpp/test-smpp-user")
                .withRestAPIBaseURL()
                .andReturns(new APIResponse(responseFixture, 200));
        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        assertReceivedExpectedResponse(messageBirdClient.getOutboundSmsPrices("test-smpp-user"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutboundSmsPricesSmppUsernameNull() throws GeneralException, UnauthorizedException, NotFoundException {
        new MessageBirdClient(mock(MessageBirdService.class)).getOutboundSmsPrices(null);
    }

    private static void assertReceivedExpectedResponse(OutboundSmsPriceResponse outboundSmsPriceResponse) {
        assertEquals(10, outboundSmsPriceResponse.getGateway());
        assertEquals("EUR", outboundSmsPriceResponse.getCurrencyCode());
        assertEquals(3, outboundSmsPriceResponse.getTotalCount());

        assertEquals(3, outboundSmsPriceResponse.getPrices().size());

        assertEquals(new BigDecimal("0.060000"), outboundSmsPriceResponse.getPrices().get(0).getPrice());
        assertEquals("EUR", outboundSmsPriceResponse.getPrices().get(0).getCurrencyCode());
        assertEquals("0", outboundSmsPriceResponse.getPrices().get(0).getMccmnc());
        assertEquals("0", outboundSmsPriceResponse.getPrices().get(0).getMcc());
        assertNull(outboundSmsPriceResponse.getPrices().get(0).getMnc());
        assertEquals("Default Rate", outboundSmsPriceResponse.getPrices().get(0).getCountryName());
        assertEquals("XX", outboundSmsPriceResponse.getPrices().get(0).getCountryIsoCode());
        assertEquals("Default Rate", outboundSmsPriceResponse.getPrices().get(0).getOperatorName());

        assertEquals(new BigDecimal("0.047000"), outboundSmsPriceResponse.getPrices().get(1).getPrice());
        assertEquals("EUR", outboundSmsPriceResponse.getPrices().get(1).getCurrencyCode());
        assertEquals("202", outboundSmsPriceResponse.getPrices().get(1).getMccmnc());
        assertEquals("202", outboundSmsPriceResponse.getPrices().get(1).getMcc());
        assertNull(outboundSmsPriceResponse.getPrices().get(1).getMnc());
        assertEquals("Greece", outboundSmsPriceResponse.getPrices().get(1).getCountryName());
        assertEquals("GR", outboundSmsPriceResponse.getPrices().get(1).getCountryIsoCode());
        assertNull(outboundSmsPriceResponse.getPrices().get(1).getOperatorName());

        assertEquals(new BigDecimal("0.045000"), outboundSmsPriceResponse.getPrices().get(2).getPrice());
        assertEquals("EUR", outboundSmsPriceResponse.getPrices().get(2).getCurrencyCode());
        assertEquals("20205", outboundSmsPriceResponse.getPrices().get(2).getMccmnc());
        assertEquals("202", outboundSmsPriceResponse.getPrices().get(2).getMcc());
        assertEquals("05", outboundSmsPriceResponse.getPrices().get(2).getMnc());
        assertEquals("Greece", outboundSmsPriceResponse.getPrices().get(2).getCountryName());
        assertEquals("GR", outboundSmsPriceResponse.getPrices().get(2).getCountryIsoCode());
        assertEquals("Vodafone", outboundSmsPriceResponse.getPrices().get(2).getOperatorName());
    }
}
