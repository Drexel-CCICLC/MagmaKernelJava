package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.struct.FieldNode;
import com.meti.node.struct.FunctionType;
import com.meti.node.struct.ObjectType;

import java.util.Optional;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.contains(".")) {
			int period = trim.indexOf('.');
			String before = trim.substring(0, period);
			String after = trim.substring(period + 1);
			Type objectType = compiler.resolveValue(before);
			if (objectType instanceof ObjectType) {
				Declaration declaration = ((ObjectType) objectType).declaration();
				return buildField(declaration, after);
			} else {
				//object could be singleton
				if (objectType instanceof FunctionType) {
					Type singletonType = compiler.resolveValue("_" + before);
					if (singletonType instanceof ObjectType) {
						Declaration declaration = ((ObjectType) singletonType).declaration();
						return buildField(declaration, after);
					} else {
						throw new ParseException(before + " is not a singleton.");
					}
				} else {
					throw new ParseException(before + " is not an object.");
				}
			}
		} else {
			Optional<Declaration> parentOptional = declarations.parent(trim);
			if (parentOptional.isEmpty()) {
				return Optional.empty();
			} else {
				Declaration parent = parentOptional.get();
				if (declarations.isRoot(parent) || (!parent.isParent() && isParameter(trim, parent))) {
					return Optional.of(new VariableNode(trim));
				} else {
					return buildField(parent, trim);
				}
			}
		}
	}

	private Optional<Node> buildField(Declaration parent, String childName) {
		Declaration child = parent.child(childName)
				.orElseThrow(() -> new ParseException(parent.name() + "." + childName + " is " +
						"not defined."));
		return child.isFunctional() ?
				Optional.of(new VariableNode(child.joinStack())) :
				Optional.of(new FieldNode(parent, childName));
	}

	private boolean isParameter(String childName, Declaration parent) {
		return parent.child(childName).orElseThrow().isParameter();
	}
}
