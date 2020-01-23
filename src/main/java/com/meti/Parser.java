package com.meti;

import com.meti.exception.ParseException;

import java.util.Optional;

public interface Parser {
    Optional<Node> parse(String content, Compiler compiler) throws ParseException;
}
