package com.meti.node.value.compound.variable;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;
import com.meti.node.bracket.struct.ObjectType;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class VariableParser implements Parser {
	private final Declarations declarations;

	public VariableParser(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}

	private Optional<Node> parse(String value, Compiler compiler) {
		String trim = value.trim();
		return trim.contains(".") ? parseAccesor(compiler, trim) : parseSimple(trim);
	}

	private Optional<Node> parseAccesor(Compiler compiler, String trim) {
		int period = trim.indexOf('.');
		String parentString = trim.substring(0, period);
		String childString = trim.substring(period + 1);
		Node parent = compiler.parseSingle(parentString);
		Type type = compiler.resolveValue(parentString);
		return buildField(type, parent, childString.trim());
	}

	private Optional<Node> parseSimple(String childName) {
		Optional<Declaration> parentOptional = findParentWithChild(childName);
		if (parentOptional.isPresent()) {
			Declaration parent = parentOptional.get();
			if (!isRoot(parent) && !isCurrent(parent) && parent.hasChildAsParameter(childName)) {
				Type type = new ObjectType(declarations, childName);
				Node firstNode = parent.toSuperVariable();
				return buildField(type, firstNode, childName);
			}
		}
		return buildInScope(childName);
	}

	private Optional<Node> buildField(Type parentType, Node parentNode, String name) {
		Optional<Type> child = parentType.childType(name.trim());
		OptionalInt order = parentType.childOrder(name.trim());
		return Optional.of(new FieldNode(parentNode, order.orElseThrow(), child.orElseThrow(), name));
	}

	private Optional<Declaration> findParentWithChild(String childName) {
		return declarations.stream()
				.filter(declaration -> declaration.child(childName).isPresent())
				.findFirst();
	}

	private boolean isRoot(Declaration obj) {
		return declarations.root().equals(obj);
	}

	private boolean isCurrent(Declaration obj) {
		return declarations.current().equals(obj);
	}

	private Optional<Node> buildInScope(String childName) {
		Optional<Declaration> optional = declarations.relative(childName);
		if (optional.isPresent()) {
			return Optional.of(optional.get().toInstance());
		} else {
			return Optional.empty();
		}
	}

}
