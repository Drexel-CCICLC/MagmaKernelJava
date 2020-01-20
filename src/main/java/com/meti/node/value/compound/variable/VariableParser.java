package com.meti.node.value.compound.variable;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;
import com.meti.node.bracket.struct.ObjectType;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return Collections.singleton(parse(value, compiler));
	}

	private Node parse(String value, Compiler compiler) {
		String trim = value.trim();
		return trim.contains(".") ? parseAccesor(compiler, trim) : parseSimple(trim);
	}

	private Node parseAccesor(Compiler compiler, String trim) {
		int period = trim.indexOf('.');
		String parentString = trim.substring(0, period);
		String childString = trim.substring(period + 1);
		Node parent = compiler.parseSingle(parentString);
		Type type = compiler.resolveValue(parentString);
		return type.toField(parent, childString.trim().trim()).orElseThrow();
	}

	private Node parseSimple(String childName) {
		Optional<Declaration> parentOptional = declarations.parentOf(childName);
		if (parentOptional.isPresent()) {
			Declaration parent = parentOptional.get();
			if (!declarations.isRoot(parent) && !declarations.isCurrent(parent) && parent.hasChildAsParameter(childName)) {
				Type type = new ObjectType(declarations, childName);
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
