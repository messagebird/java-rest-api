package com.messagebird.objects.integrations;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HSMComponentTest {
    @Test
    public void testToString() {
        HSMComponent component = new HSMComponent();
        component.setType(HSMComponentType.BODY);
        component.setFormat(HSMComponentFormat.TEXT);
        component.setText("Test text");
        component.setAddSecurityRecommendation(true);
        component.setCodeExpirationMinutes(10);
        component.setHasExpiration(true);

        String expected = "HSMComponent{type=BODY, format=TEXT, text='Test text', addSecurityRecommendation=true, codeExpirationMinutes=10, buttons=null, hasExpiration=true, cards=null, example=null}";
        assertEquals(expected, component.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTextInvalid() {
        HSMComponent component = new HSMComponent();
        component.setText("");
    }
}
