package com.messagebird.objects.conversations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TemplateMediaTypeTest {
    @Test
    public void testTemplateMediaTypeForValueValid() {
        assertEquals(TemplateMediaType.VIDEO, TemplateMediaType.forValue("video"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemplateMediaTypeForValueNull() {
        TemplateMediaType.forValue(null);
    }

    @Test
    public void testTemplateMediaTypeForValueInvalid() {
        assertNull(TemplateMediaType.forValue("non_existing_value"));
    }

    @Test
    public void testTemplateMediaTypeToString() {
        assertEquals("video", TemplateMediaType.VIDEO.toString());
    }
}
