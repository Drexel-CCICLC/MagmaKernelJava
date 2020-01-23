package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntParserTest {
    @Test
    void testWithout() {
        IntParser parser = new IntParser();
        Node node = parser.parse("10").orElseThrow();
        assertEquals("10i", node.render());
    }

    @Test
    void testWith() {
        IntParser parser = new IntParser();
        Node node = parser.parse("10i").orElseThrow();
        assertEquals("10i", node.render());
    }

    @Test
    void testInvalid() {
        IntParser parser = new IntParser();
        assertTrue(parser.parse("test").isEmpty());
    }
}
