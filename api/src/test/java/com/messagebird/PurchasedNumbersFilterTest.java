package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.objects.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class PurchasedNumbersFilterTest {

    @Test
    public void testDefaults() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        // Test
        assertEquals(10, filter.getLimit());
        assertEquals(0, filter.getOffset());
        assertEquals(0, filter.getFeatures().size());
        assertEquals(0, filter.getTags().size());
        assertNull(filter.getNumber());
        assertNull(filter.getRegion());
        assertNull(filter.getLocality());
        assertNull(filter.getType());
    }

    @Test
    public void testAddingFeatures() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        assertEquals(EnumSet.noneOf(PhoneNumberFeature.class), filter.getFeatures());

        // Test can add a feature
        filter.addFeature(PhoneNumberFeature.SMS);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS), filter.getFeatures());

        // Test can have multiple features
        filter.addFeature(PhoneNumberFeature.VOICE);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE), filter.getFeatures());

        // Test shouldn't have more than one of each
        filter.addFeature(PhoneNumberFeature.SMS);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE), filter.getFeatures());

        filter.addFeature(PhoneNumberFeature.MMS);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE, PhoneNumberFeature.MMS), filter.getFeatures());
    }

    @Test
    public void testAddingMultipleFeatures() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        assertEquals(EnumSet.noneOf(PhoneNumberFeature.class), filter.getFeatures());

        // Test
        filter.addFeature(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE), filter.getFeatures());
    }

    @Test
    public void testRemovingFeatures() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        // Test
        filter.addFeature(PhoneNumberFeature.SMS, PhoneNumberFeature.MMS, PhoneNumberFeature.VOICE);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE, PhoneNumberFeature.MMS), filter.getFeatures());

        filter.removeFeature(PhoneNumberFeature.VOICE);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.MMS), filter.getFeatures());

        filter.removeFeature(PhoneNumberFeature.VOICE);
        assertEquals(EnumSet.of(PhoneNumberFeature.SMS, PhoneNumberFeature.MMS), filter.getFeatures());

        filter.removeFeature(PhoneNumberFeature.SMS, PhoneNumberFeature.MMS);
        assertEquals(EnumSet.noneOf(PhoneNumberFeature.class), filter.getFeatures());
    }

    @Test
    public void testAddingTags() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        assertArrayEquals(new String[]{}, filter.getTags().toArray());

        // Test
        filter.addTag("TEST_TAG");
        assertArrayEquals(new String[]{"TEST_TAG"}, filter.getTags().toArray());

        filter.addTag("Another test tag");
        assertArrayEquals(new String[]{"TEST_TAG", "Another test tag"}, filter.getTags().toArray());

        filter.addTag("a", "b", "c");
        assertArrayEquals(new String[]{"TEST_TAG", "Another test tag", "a", "b", "c"}, filter.getTags().toArray());
    }

    @Test
    public void testAddingDuplicateTags() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        filter.addTag("a");
        filter.addTag("a");
        filter.addTag("a", "b", "b", "c", "b");

        assertArrayEquals(new String[]{"a", "b", "c"}, filter.getTags().toArray());
    }

    @Test
    public void testRemoveTags() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        filter.addTag("a", "b", "c", "d");
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, filter.getTags().toArray());

        // Test
        filter.removeTag("b");
        assertArrayEquals(new String[]{"a", "c", "d"}, filter.getTags().toArray());

        filter.removeTag("d");
        assertArrayEquals(new String[]{"a", "c"}, filter.getTags().toArray());

        filter.removeTag("b", "c", "d");
        assertArrayEquals(new String[]{"a"}, filter.getTags().toArray());
    }

    @Test
    public void testClearTags() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        filter.addTag("a", "b", "c", "d");
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, filter.getTags().toArray());

        // Test
        filter.clearTags();
        assertArrayEquals(new String[]{}, filter.getTags().toArray());
    }

    @Test
    public void testBasicSetters() {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        assertNull(filter.getNumber());
        assertNull(filter.getRegion());
        assertNull(filter.getLocality());
        assertNull(filter.getType());

        // Test
        filter.setLimit(23);
        filter.setOffset(5);
        filter.setNumber("TEST_NUMBER");
        filter.setRegion("TEST_REGION");
        filter.setLocality("TEST_LOCALITY");
        filter.setType(PhoneNumberType.MOBILE);

        assertEquals(23, filter.getLimit());
        assertEquals(5, filter.getOffset());
        assertEquals("TEST_NUMBER", filter.getNumber());
        assertEquals("TEST_REGION", filter.getRegion());
        assertEquals("TEST_LOCALITY", filter.getLocality());
        assertEquals(PhoneNumberType.MOBILE, filter.getType());
    }

    @Test
    public void testToHashMapWithDefaultValues() throws GeneralException {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        HashMap<String, Object> map = filter.toHashMap();

        assertEquals(10, map.get("limit"));
        assertEquals(0, map.get("offset"));
        assertEquals(EnumSet.noneOf(PhoneNumberFeature.class), map.get("features"));
        assertEquals(new ArrayList<String>(), map.get("tags"));
    }

    @Test
    public void testToHashMapWithAllValues() throws GeneralException {
        // Setup
        PurchasedNumbersFilter filter = new PurchasedNumbersFilter();

        filter.setLimit(42);
        filter.setOffset(24);
        filter.setNumber("1234567890");
        filter.setRegion("My Region");
        filter.setLocality("My Locality");
        filter.setType(PhoneNumberType.MOBILE);
        filter.addFeature(PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE);
        filter.addTag("h", "e", "l", "l", "o");

        HashMap<String, Object> map = filter.toHashMap();

        assertEquals(42, map.get("limit"));
        assertEquals(24, map.get("offset"));
        assertEquals("1234567890", map.get("number"));
        assertEquals("My Region", map.get("region"));
        assertEquals("My Locality", map.get("locality"));
        assertEquals("mobile", map.get("type").toString());
        assertArrayEquals(new PhoneNumberFeature[]{PhoneNumberFeature.SMS, PhoneNumberFeature.VOICE}, ((Collection<Object>) map.get("features")).toArray());

        assertArrayEquals(new String[]{"h", "e", "l", "o"}, ((Collection<Object>) map.get("tags")).toArray());
    }
}
