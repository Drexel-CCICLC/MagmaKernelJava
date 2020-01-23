package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentParserTest {

    @Test
    void parse() throws ParseException {
        Parser parser = new ParentParser(
                new IntParser(),
                new CharParser()
        );
        assertEquals("'x'", parser.parse("'x'").orElseThrow().render());
        assertEquals("10i", parser.parse("10").orElseThrow().render());
    }
}