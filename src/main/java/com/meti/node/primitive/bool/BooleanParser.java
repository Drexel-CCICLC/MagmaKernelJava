package com.meti.node.primitive.bool;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class BooleanParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.equals("true")) {
            return Optional.of(new BooleanNode(true));
        } else if (trim.equals("false")) {
            return Optional.of(new BooleanNode(false));
        }
        return Optional.empty();
    }
}
