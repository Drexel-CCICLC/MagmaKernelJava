package com.meti.node.primitive;

import com.meti.node.Node;
import com.meti.Parser;
import com.meti.exception.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharParserTest {

    @Test
    void parseSimple() throws ParseException {
        Parser parser = new CharParser();
        Node node = parser.parse("'x'", null).orElseThrow();
        assertEquals("'x'", node.render());
    }

    @Test
    void tooManyCharacters() {
        Parser parser = new CharParser();
        assertThrows(ParseException.class, () -> parser.parse("'ax'", null));
    }
}