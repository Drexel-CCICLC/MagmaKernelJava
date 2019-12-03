package com.meti.interpret;

import com.meti.assemble.DeclareNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.Optional;

class DeclarationPattern implements Pattern {
    @Override
    public Optional<Statement> match(Node node, Interpreter interpreter) {
        if (node instanceof DeclareNode) {
            Node value = node.getProperty(NodeProperty.VALUE);
            return Optional.of(new DeclareStatement(
                    interpreter.resolve(value),
                    node.getProperty(NodeProperty.MUTABLE),
                    node.getProperty(NodeProperty.NAME),
                    interpreter.interpret(node).orElseThrow()
            ));
        } else {
            return Optional.empty();
        }
    }
}
