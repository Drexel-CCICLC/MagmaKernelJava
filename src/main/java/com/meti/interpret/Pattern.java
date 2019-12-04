package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.Optional;

interface Pattern {
    Optional<Statement> match(Node node, Interpreter interpreter);
}
