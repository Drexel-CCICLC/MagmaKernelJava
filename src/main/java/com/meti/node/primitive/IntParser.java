package com.meti.node.primitive;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class IntParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        String intValue = trim.endsWith("i") ?
                trim.substring(0, trim.length() - 1) :
                trim;
        try {
            return Optional.of(new IntNode(Integer.parseInt(intValue)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
