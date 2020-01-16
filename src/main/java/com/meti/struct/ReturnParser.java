package com.meti.struct;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class ReturnParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("return ")) {
            String valueString = trim.substring(7);
            Node node = compiler.parse(valueString);
            return Optional.of(new ReturnNode(node));
        }
        return Optional.empty();
    }
}
