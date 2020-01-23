package com.meti.node.value.compound.variable;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.ObjectType;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;

import java.util.Optional;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		return Optional.of(trim.contains(".") ? parseAccesor(compiler, trim) : parseSimple(trim));
	}

	private Node parseAccesor(Compiler compiler, String trim) {
		int period = trim.indexOf('.');
		String parentString = trim.substring(0, period);
		String childString = trim.substring(period + 1);
		Node parent = compiler.parseSingle(parentString);
		Type resolvedType = compiler.resolveValue(parentString);
		ObjectType type;
		if (resolvedType instanceof ObjectType) {
			type = (ObjectType) resolvedType;
		} else {
			throw new IllegalArgumentException(resolvedType + " is not a structure.");
		}
		return type.toField(parent, childString.trim().trim()).orElseThrow();
	}

	private Node parseSimple(String childName) {
		Optional<Declaration> parentOptional = declarations.parentOf(childName);
		if (parentOptional.isPresent()) {
			Declaration parent = parentOptional.get();
			if (!declarations.isRoot(parent) && !declarations.isCurrent(parent) && parent.hasChildAsParameter(childName)) {
				ObjectType type = parent.toObject();
				Node instance = parent.toInstance();
				return type.toField(instance, childName.trim()).orElseThrow();
			}
		}
		return buildInScope(childName);
	}

	private Node buildInScope(String childName) {
		return declarations.relative(childName)
				.map(Declaration::toParameter)
				.orElseThrow();
	}
}
