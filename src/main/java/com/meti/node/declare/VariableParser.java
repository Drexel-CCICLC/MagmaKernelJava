package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.node.struct.FieldNode;

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
			if (declarations.isRoot(parent) || (!parent.isParent() && parent.child(trim).orElseThrow() instanceof ParameterDeclaration)) {
				return Optional.of(new VariableNode(trim));
			} else {
				return Optional.of(new FieldNode(new VariableNode(parent.instanceName()), trim));
			}
		}
	}
}
