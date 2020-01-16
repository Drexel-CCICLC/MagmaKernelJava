package com.meti.variable;

import com.meti.Compiler;
import com.meti.*;
import com.meti.struct.ObjectType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains(".")) {
			int period = trim.indexOf('.');
			String parentString = trim.substring(0, period);
			String childString = trim.substring(period + 1);
			Node parent = compiler.parse(parentString);
			Type type = compiler.resolveValue(parentString);
			return buildField(type, parent, childString);
		} else {
			Optional<Declaration> first = declarations.stackStream()
					.filter(declaration -> declaration.child(trim).isPresent())
					.findFirst();
			if (first.isPresent() && !"root".equals(first.get()) && !first.get().equals(declarations.current())) {
				Optional<Declaration> child = first.get().child(trim);
				if (child.isPresent() && child.get().isParameter()) {
					Map<String, Type> declaration = first.orElseThrow()
							.childMap();
					Type type = new ObjectType(declaration);
					Node firstNode = new VariableNode(first.get().name() + "_");
					return buildField(type, firstNode, trim);
				}
			}
			return declarations.relative(trim.trim())
					.map(Declaration::name)
					.map(VariableNode::new);
		}
	}

	private Optional<Node> buildField(Type parentType, Node parentNode, String name) {
		Optional<Type> child = parentType.childType(name.trim());
		OptionalInt order = parentType.childOrder(name.trim());
		return Optional.of(new FieldNode(parentNode, order.orElseThrow(), child.orElseThrow(), name));
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
