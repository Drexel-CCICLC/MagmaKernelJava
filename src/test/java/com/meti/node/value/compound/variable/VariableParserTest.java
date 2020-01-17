package com.meti.node.value.compound.variable;

import com.meti.declare.DeclarationBuilder;
import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.meti.node.bracket.struct.StructTypeBuilder.create;
import static org.junit.jupiter.api.Assertions.*;

class VariableParserTest {

    @Test
    void parseMultiple() {
        Declarations declarations = new TreeDeclarations();
        declarations.define("a", create()
                .withReturnType(IntType.INSTANCE)
                .withParameter(IntType.INSTANCE)
                .build(),
                () -> declarations.define("value", DeclarationBuilder.create()
                        .withName("value")
                        .withType(IntType.INSTANCE)
                        .flagAsParameter()));
        Parser parser = new VariableParser(declarations);
        Collection<Node> nodes = parser.parseMultiple("value", null);
        assertFalse(nodes.isEmpty());
        assertEquals("*(int*)a_[0]", nodes.toArray(Node[]::new)[0].render());
    }
}