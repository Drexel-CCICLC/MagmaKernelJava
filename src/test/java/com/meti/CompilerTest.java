package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    private final Compiler compiler = new Compiler(new ParentParser(
            new IntParser(),
            new CharParser()
    ));

    @Test
    void compile() throws ParseException {
        assertEquals("'x'", compiler.parse("'x'").orElseThrow().render());
        assertEquals("10i", compiler.parse("10").orElseThrow().render());
    }
}
