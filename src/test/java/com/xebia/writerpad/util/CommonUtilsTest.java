package com.xebia.writerpad.util;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.xebia.writerpad.constant.Constants.SINGLE_SPACE;
import static com.xebia.writerpad.util.CommonUtils.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CommonUtilsTest {

    @Test
    public void testEmptyCollection() {
        assertEquals(isEmpty(Collections.emptyList()), true);
    }

    @Test
    public void testNotEmptyCollection() {
        assertEquals(isNotEmpty(Collections.singleton(1)), true);
    }

    @Test
    public void testEmptyArray() {
        Object[] a = {};
        assertEquals(isEmpty(a), true);
    }

    @Test
    public void testNotEmptyArray() {
        Object[] a = {1, 2};
        assertEquals(isEmpty(a), false);
    }

    @Test
    public void testBlankStringWithEMPTY() {
        assertEquals(isBlank(EMPTY), true);
    }

    @Test
    public void testBlankStringWithNull() {
        assertEquals(isBlank(null), true);
    }

    @Test
    public void testBlankStringWithSpace() {
        assertEquals(isBlank(SINGLE_SPACE), true);
    }

    @Test
    public void testNotBlankString() {
        assertEquals(isNotBlank("Hello"), true);
    }
}
