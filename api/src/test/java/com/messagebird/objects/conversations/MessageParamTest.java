package com.messagebird.objects.conversations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageParamTest {
    @Test
    public void testMessageParamToString() {
        MessageParam param = new MessageParam();
        param.setType(TemplateMediaType.IMAGE);
        param.setText("Sample text");
        param.setPayload("Sample payload");

        String expected = "MessageParam{type=image, text='Sample text', payload='Sample payload', currency=null, dateTime='null', document=null, image=null, video=null, expirationTime='null'}";
        assertEquals(expected, param.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMessageParamSetTextInvalid() {
        MessageParam param = new MessageParam();
        param.setText("");
    }

    @Test
    public void testMessageParamSetTextValid() {
        MessageParam param = new MessageParam();
        param.setText("Valid text");
        assertEquals("Valid text", param.getText());
    }
}
