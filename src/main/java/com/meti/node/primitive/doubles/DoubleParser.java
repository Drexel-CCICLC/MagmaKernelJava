package com.meti.node.primitive.doubles;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class DoubleParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        String doubleValue = trim.endsWith("d") ?
                trim.substring(0, trim.length() - 1) :
                trim;
        try {
            return Optional.of(new DoubleNode(Double.parseDouble(doubleValue)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
