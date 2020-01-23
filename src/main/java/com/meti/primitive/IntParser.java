package com.meti.primitive;

import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class IntParser implements Parser {
    @Override
    public Optional<Node> parse(String value) {
        String trim = value.trim();
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
