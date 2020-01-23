package com.meti;

import com.meti.exception.ParseException;

import java.util.Optional;

public class DeclareParser implements Parser {
    @Override
    public Optional<Node> parse(String content, Compiler compiler) throws ParseException {
        if (content.contains("=")) {
            int equalsIndex = content.indexOf('=');
            String beforeEquals = content.substring(0, equalsIndex);
            String afterEquals = content.substring(equalsIndex + 1);
            Node value
        }
        return Optional.empty();
    }
}
