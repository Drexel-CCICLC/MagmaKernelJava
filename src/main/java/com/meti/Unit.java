package com.meti;

import java.util.Optional;

public interface Unit {
	Optional<String> compile(Node root, Compiler compiler);

	Optional<Node> parse(String value, Compiler compiler);

	Optional<Node> transform(Node root, Compiler compiler);
}
