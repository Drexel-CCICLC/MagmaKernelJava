package com.meti.interpret;

import com.meti.assemble.DeclareNode;
import com.meti.assemble.Node;

import java.util.Map;
import java.util.Optional;

import static com.meti.assemble.NodeProperty.*;

class DeclarePattern implements Pattern {
    private final Map<String, Type> variables;

    DeclarePattern(Map<String, Type> variables) {
        this.variables = variables;
    }

    @Override
    public Optional<Statement> match(Node node, Interpreter interpreter) {
        if (node.hasProperty(ID) && node.getProperty(ID).equals(DeclareNode.ID)) {
            Node value = node.getProperty(VALUE);
            Type type = interpreter.resolve(value);
            String name = node.getProperty(NAME);
            if (variables.containsKey(name)) {
                throw new IllegalArgumentException(name + " has already been defined.");
            }
            variables.put(name, type);
            return Optional.of(new DeclareStatement(
                    type, node.getProperty(MUTABLE),
                    name, interpreter.interpret(value).orElseThrow()
            ));
        } else {
            return Optional.empty();
        }
    }
}
