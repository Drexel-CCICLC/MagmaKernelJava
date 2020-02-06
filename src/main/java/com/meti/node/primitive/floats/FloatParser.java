package com.meti.node.primitive.floats;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Optional;

public class FloatParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) {
        String trim = content.trim();
        String floatValue = trim.endsWith("f") ?
                trim.substring(0, trim.length() - 1) :
                trim;
        try {
            return Optional.of(new FloatNode(Float.parseFloat(floatValue)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
