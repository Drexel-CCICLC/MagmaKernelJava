package com.meti;

import com.meti.node.Node;

import java.util.Optional;

public interface Parser {
	Optional<Node> parse(String content, Compiler compiler);
}
