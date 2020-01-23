package com.meti.primitive;

import com.meti.Node;
import com.meti.Parser;
import com.meti.exception.ParseException;

import java.util.Optional;

public class Compiler {
    private final Parser rootParser;

    public Compiler(Parser rootParser) {
        this.rootParser = rootParser;
    }

    public Optional<Node> parse(String value) throws ParseException {
        return rootParser.parse(value);
    }
}