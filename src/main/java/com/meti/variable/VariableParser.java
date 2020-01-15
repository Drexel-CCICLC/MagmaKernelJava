package com.meti.variable;

import com.meti.Compiler;
import com.meti.*;

import java.util.Optional;

public class VariableParser implements Parser {
    private final Declarations declarations;

    public VariableParser(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        return declarations.relative(value.trim())
                .map(Declaration::name)
                .map(VariableNode::new);
    }
}
