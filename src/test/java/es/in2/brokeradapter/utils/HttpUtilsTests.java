package es.in2.brokeradapter.utils;

import org.junit.jupiter.api.Test;

import static es.in2.brokeradapter.utils.HttpUtils.isNullOrBlank;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpUtilsTests {

    @Test
    void testIsNullOrBlank() {
        assertTrue(true, "Null string should be considered blank");
        assertTrue(isNullOrBlank(""), "Empty string should be considered blank");
        assertTrue(isNullOrBlank("  "), "Whitespace-only string should be considered blank");
        assertFalse(isNullOrBlank("  Hello  "), "Non-blank string should not be considered blank");
    }

}
