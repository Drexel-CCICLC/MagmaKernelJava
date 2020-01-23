package com.meti;

import java.util.Optional;

public interface Parser {
    Optional<Node> parse(String value) throws ParseException;
}
