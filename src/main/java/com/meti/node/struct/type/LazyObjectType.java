package com.meti.node.struct.type;

import com.meti.node.Type;
import com.meti.node.ValueType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Collection;
import java.util.Optional;

public class LazyObjectType extends ValueType implements ObjectType {
	private final Declarations declarations;
	private final Collection<String> stack;

	public LazyObjectType(Declarations declarations, Collection<String> stack) {
		this.declarations = declarations;
		this.stack = stack;
	}

	@Override
	public Optional<Type> childType(String child) {
		return declaration()
				.child(child)
				.map(Declaration::type);
	}

	@Override
	public Declaration declaration() {
		return declarations.absolute(stack);
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
		return new StructType(declaration().name()).render(name);
	}
}
