package com.meti.primitive;

import com.meti.ParentParser;
import com.meti.Parser;
import com.meti.exception.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentParserTest {

    @Test
    void parse() throws ParseException {
        Parser parser = new ParentParser(
                new IntParser(),
                new CharParser()
        );
        assertEquals("'x'", parser.parse("'x'", null).orElseThrow().render());
        assertEquals("10i", parser.parse("10", null).orElseThrow().render());
    }
}