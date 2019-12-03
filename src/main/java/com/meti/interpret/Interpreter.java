package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.List;

interface Interpreter {
    void interpret(List<Node> nodes);

    List<Statement> collect();
}
