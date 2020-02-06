package com.meti;

import com.meti.exception.ParseException;
import com.meti.node.primitive.chars.CharParser;
import com.meti.node.primitive.ints.IntParser;
import com.meti.util.ParentParser;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    private final Compiler compiler = new UnitCompiler(new ParentParser(
            new IntParser(),
            new CharParser()
    ), null);

    @Test
    void compile() throws ParseException {
        assertEquals("'x'", compiler.parse("'x'").render());
        assertEquals("10", compiler.parse("10").render());
    }
}
