package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntParserTest {
    @Test
    void testWithout() throws ParseException {
        Parser parser = new IntParser();
        Node node = parser.parse("10").orElseThrow();
        assertEquals("10i", node.render());
    }

    @Test
    void testWith() throws ParseException {
        Parser parser = new IntParser();
        Node node = parser.parse("10i").orElseThrow();
        assertEquals("10i", node.render());
    }

    @Test
    void testInvalid() throws ParseException {
        Parser parser = new IntParser();
        assertTrue(parser.parse("test").isEmpty());
    }
}
