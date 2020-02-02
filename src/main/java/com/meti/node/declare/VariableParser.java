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
			if (declarations.isRoot(parent) || (!parent.isParent() && isParameter(trim, parent))) {
				return Optional.of(new VariableNode(trim));
			} else {
				Declaration child = parent.child(trim).orElseThrow();

				if (!child.isFunctional()) {
					return Optional.of(new FieldNode(parent, trim));
				} else {
					return Optional.of(new VariableNode(child.joinStack()));
				}
			}
		}
	}

	private boolean isParameter(String childName, Declaration parent) {
		return parent.child(childName).orElseThrow().isParameter();
	}
}
