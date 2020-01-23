package com.meti;

import java.util.Optional;

public class CharParser implements Parser {
    @Override
    public Optional<Node> parse(String value) throws ParseException {
        String trim = value.trim();
        if (trim.startsWith("'") && trim.endsWith("'")) {
            if (trim.length() == 3) {
                return Optional.of(new CharNode(trim.charAt(1)));
            } else {
                throw new ParseException(trim + " has too many characters.");
            }
        }
        return Optional.empty();
    }
}
