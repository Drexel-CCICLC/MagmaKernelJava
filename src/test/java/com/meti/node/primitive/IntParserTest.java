package com.meti.node.primitive;

import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.primitive.ints.IntParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntParserTest {
    @Test
    void testInvalid() throws ParseException {
        Parser parser = new IntParser();
        assertTrue(parser.parse("test", null).isEmpty());
    }

    @Test
    void testWith() throws ParseException {
        Parser parser = new IntParser();
        Node node = parser.parse("10i", null).orElseThrow();
        assertEquals("10", node.render());
    }

    @Test
    void testWithout() throws ParseException {
        Parser parser = new IntParser();
        Node node = parser.parse("10", null).orElseThrow();
        assertEquals("10", node.render());
    }
}
