package com.meti.node.struct;

import com.meti.node.ValueType;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.Collection;

public class ObjectTypeImpl extends ValueType implements ObjectType {
	private final Declarations declarations;
	private final Collection<String> stack;

	public ObjectTypeImpl(Declarations declarations, Collection<String> stack) {
		this.declarations = declarations;
		this.stack = stack;
	}

	@Override
	public String render() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String render(String name) {
		return new StructType(declaration().name()).render(name);
	}

	@Override
	public Declaration declaration() {
		return declarations.absolute(stack);
	}

	@Override
	public String toMagmaString() {
		return "";
	}
}
