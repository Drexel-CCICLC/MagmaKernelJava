package com.meti.node.struct.type;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.ValueType;
import com.meti.node.declare.VariableNode;
import com.meti.parse.Declaration;

import java.util.Optional;

public class DefinedObjectType extends ValueType implements ObjectType {
	private final Declaration declaration;

	public DefinedObjectType(Declaration declaration) {
		this.declaration = declaration;
	}

	@Override
	public Optional<Type> childType(String child) {
		return declaration.child(child).map(Declaration::type);
	}

	@Override
	public Declaration declaration() {
		return declaration;
	}

	@Override
	public Node childToNode(String parent, String child) {
		if (declaration().hasChild(child)) {
			return new VariableNode(parent);
		} else {
			throw new ParseException(parent + "." + child + " is not defined.");
		}
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
		return new StructType(declaration.name()).render(name);
	}
}
