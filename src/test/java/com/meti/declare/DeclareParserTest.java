package com.meti.declare;

import com.meti.Compiler;
import com.meti.*;
import com.meti.other.AnyResolver;
import com.meti.other.VoidResolver;
import com.meti.string.StringResolver;
import com.meti.struct.StructParser;
import com.meti.struct.StructResolver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeclareParserTest {
    private final Declarations declarations = new Declarations();

    @Test
    void bug0() {
        Compiler compiler = new UnitCompiler(new ParentParser(new StructParser(declarations, new ArrayList<>())), new ParentResolver(
                new StructResolver(),
                new StringResolver(),
                new AnyResolver(),
                new VoidResolver()));
        Parser parser = new DeclareParser(declarations);
        Optional<Node> optional = parser.parse("native val printf = (String format, Any value) => Void", compiler);
        assertTrue(optional.isPresent());
        assertSame(EmptyNode.class, optional.get().getClass());
    }
}