package com.messagebird.objects.conversations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MessageComponentTypeTest {
    @Test
    public void testMessageComponentTypeForValueValid() {
        assertEquals(MessageComponentType.HEADER, MessageComponentType.forValue("header"));
        assertEquals(MessageComponentType.BUTTON, MessageComponentType.forValue("button"));
    }

    @Test(expected = NullPointerException.class)
    public void testMessageComponentTypeForValueNull() {
        MessageComponentType.forValue(null);
    }

    @Test
    public void testMessageComponentTypeForValueInvalid() {
        assertNull(MessageComponentType.forValue("invalid_type"));
    }

    @Test
    public void testMessageComponentTypeToString() {
        assertEquals("header", MessageComponentType.HEADER.toString());
    }
}
