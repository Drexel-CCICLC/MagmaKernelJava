package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Interpreter {
    Type resolve(Node node);

    Type resolve(String name);

    Optional<Statement> interpret(Node node);

    void interpret(Stream<Node> nodes);

    List<Statement> collect();
}
