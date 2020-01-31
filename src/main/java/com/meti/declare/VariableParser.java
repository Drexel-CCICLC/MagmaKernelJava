package com.meti.declare;

import com.meti.Compiler;
import com.meti.FieldNode;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class VariableParser implements Parser {
	private final TreeDeclarations declarations;

	public VariableParser(TreeDeclarations declarations) {
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
			if (declarations.isRoot(parent) || (!parent.isParent() && parent.child(trim).orElseThrow() instanceof ParameterDeclaration)) {
				return Optional.of(new VariableNode(trim));
			} else {
				return Optional.of(new FieldNode(new VariableNode(parent.instanceName()), trim));
			}
		}
	}
}
