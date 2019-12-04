package com.meti.interpret;

import com.meti.assemble.IntNode;
import com.meti.assemble.Node;
import com.meti.assemble.StringNode;

import java.util.Optional;

import static com.meti.assemble.NodeProperty.VALUE;
import static com.meti.interpret.PrimitiveType.INT;

class PrimitivePattern implements Pattern {
    @Override
    public Optional<Statement> match(Node node, Interpreter interpreter) {
        Object value = node.getProperty(VALUE);
        if (node instanceof IntNode) {
            return Optional.of(new PrimitiveStatement(INT, value));
        } else if (node instanceof StringNode) {
            return Optional.of(new PrimitiveStatement(PrimitiveType.STRING, value));
        } else {
            return Optional.empty();
        }
    }
}
