package com.meti;

import java.util.Optional;

public class PrimitiveUnit implements Unit {
	@Override
	public Optional<String> compile(Node root, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Node> transform(Node root, Compiler compiler) {
		return Optional.empty();
	}
}
