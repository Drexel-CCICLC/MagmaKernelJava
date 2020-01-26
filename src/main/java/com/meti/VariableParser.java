package com.meti;

import java.util.Optional;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		Optional<Declaration> parentOptional = declarations.parent(trim);
		if (parentOptional.isEmpty()) {
			return Optional.empty();
		} else {
			Declaration parent = parentOptional.get();
			if (declarations.isRoot(parent)) {
				return Optional.of(new VariableNode(trim));
			} else {
				String parentName = parent.getName();
				return Optional.of(new FieldNode(new VariableNode(parentName + "_"), trim));
			}
		}
	}
}
