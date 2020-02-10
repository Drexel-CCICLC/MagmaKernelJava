package com.meti.node.condition;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class ElseParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        return Optional.of(content)
                .map(String::trim)
                .filter(s -> s.startsWith("else"))
                .map(s -> s.substring(4))
                .map(compiler::parse)
                .map(ElseNode::new);
    }
}
