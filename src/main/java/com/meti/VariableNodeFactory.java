package com.meti;

import java.util.Optional;

public class VariableNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public VariableNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser, Node parent) {
		if (value.isBlank()) return Optional.empty();
		var trimmedValue = value.trim();
		var args = trimmedValue.split("\\.");
		var declaration = tree.locateDeclaration(args)
				.orElseThrow(() -> new IllegalStateException(trimmedValue + " is not defined."));
		return Optional.of(new VariableNode(declaration.struct(), args));
	}

	@Override
	public Optional<Struct> parse(String value) {
		var declaration = tree.locateDeclaration(value.split("\\."));
		return declaration.map(node -> new ObjectStruct(value, node));
	}

}
