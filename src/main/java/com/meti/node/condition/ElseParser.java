package com.meti.node.condition;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class ElseParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.startsWith("else")) {
            String blockString = trim.substring(4);
            Node block = compiler.parse(blockString);
            return Optional.of(new ElseNode(block));
        }
        return Optional.empty();
    }
}
