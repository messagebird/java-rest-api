package com.messagebird.objects.integrations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HSMComponentTypeTest {
    @Test
    public void testForValueValid() {
        assertEquals(HSMComponentType.BODY, HSMComponentType.forValue("BODY"));
        assertEquals(HSMComponentType.BODY, HSMComponentType.forValue("body"));
    }

    @Test(expected = NullPointerException.class)
    public void testForValueNull() {
        HSMComponentType.forValue(null);
    }

    @Test
    public void testForValueInvalid() {
        assertNull(HSMComponentType.forValue("INVALID"));
    }
}
