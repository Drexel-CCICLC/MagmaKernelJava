package com.meti.node;

import com.meti.compile.Compiler;

import java.util.Optional;

public interface Parser {
	Optional<Node> parse(String value, Compiler compiler);
}
