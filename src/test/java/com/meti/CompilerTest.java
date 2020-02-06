package com.meti;

import com.meti.core.ParentParser;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.primitive.CharParser;
import com.meti.node.primitive.IntParser;
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
