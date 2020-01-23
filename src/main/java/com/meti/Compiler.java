package com.meti;

import java.util.Optional;

public class Compiler {
    private final Parser rootParser;

    public Compiler(Parser rootParser) {
        this.rootParser = rootParser;
    }

    Optional<Node> parse(String value) throws ParseException {
        return rootParser.parse(value);
    }
}