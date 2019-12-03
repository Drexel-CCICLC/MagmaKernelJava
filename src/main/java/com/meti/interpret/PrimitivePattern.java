package com.meti.interpret;

import com.meti.assemble.IntNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.Optional;

class PrimitivePattern implements Pattern {
    @Override
    public Optional<Statement> match(Node node, Interpreter interpreter) {
        if (node instanceof IntNode) return Optional.empty();
        int value = node.getProperty(NodeProperty.VALUE);
        return Optional.of(new PrimitiveStatement(PrimitiveType.INT, value));
    }
}
