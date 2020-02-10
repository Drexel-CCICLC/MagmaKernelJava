package com.meti.node.struct.type;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.ValueType;
import com.meti.node.declare.VariableNode;
import com.meti.parse.Declaration;

import java.util.Optional;

public class DefinedStructType extends ValueType implements StructType {
	private final Declaration declaration;

	public DefinedStructType(Declaration declaration) {
		this.declaration = declaration;
	}

	@Override
	public Node bind(String instanceName, String child) {
		if (declaration().hasChild(child)) {
			return new VariableNode(instanceName);
		} else {
			throw new ParseException(instanceName + "." + child + " is not defined.");
		}
	}

	@Override
	public Declaration declaration() {
		return declaration;
	}

	@Override
	public Optional<Type> typeOf(String child) {
		return declaration.child(child).map(Declaration::type);
	}

	@Override
	public String toMagmaString() {
		return "";
	}

	@Override
	public String render() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String render(String name) {
		return new NativeStructType(declaration.name()).render(name);
	}
}
