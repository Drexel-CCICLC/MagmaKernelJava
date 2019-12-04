package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Interpreter {
    Type resolve(Node node);

    //TODO: add test
    Type resolve(String name);

    Optional<Statement> interpret(Node node);

    void interpret(Stream<? extends Node> nodes);

    List<Statement> collect();
}
