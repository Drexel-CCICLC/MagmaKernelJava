package com.meti.node.struct;

import com.meti.node.Type;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.Collection;

public class ObjectType implements Type {
	private final Declarations declarations;
	private final Collection<String> stack;

	public ObjectType(Declarations declarations, Collection<String> stack) {
		this.declarations = declarations;
		this.stack = stack;
	}

	@Override
	public String render() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String render(String name) {
		return new StructType(lazyDeclaration().name()).render(name);
	}

	private Declaration lazyDeclaration() {
		return declarations.absolute(stack);
	}
}
