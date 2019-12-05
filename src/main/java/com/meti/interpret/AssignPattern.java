package com.meti.interpret;

import com.meti.assemble.AssignNode;
import com.meti.assemble.Node;

import java.util.Map;
import java.util.Optional;

import static com.meti.assemble.NodeProperty.*;

class AssignPattern implements Pattern {
    private Map<String, Type> variables;

    public AssignPattern(Map<String, Type> variables) {
        this.variables = variables;
    }

    @Override
    public Optional<Statement> match(Node node, Interpreter interpreter) {
        if (node.hasProperty(ID) && node.getProperty(ID).equals(AssignNode.ID)) {
            Node value = node.getProperty(VALUE);
            Type type = interpreter.resolve(value);
            String name = node.getProperty(NAME);
            if (!variables.containsKey(name)) {
                throw new IllegalArgumentException(name + " has not been defined.");
            }
            if (!variables.get(name).equals(type)) {
                throw new IllegalArgumentException("Type " + type + " cannot be assigned to " + variables.get(name));
            }
            return Optional.of(new AssignStatement(
                    type, name,
                    interpreter.interpret(value).orElseThrow()
            ));
        } else {
            return Optional.empty();
        }
    }
}
