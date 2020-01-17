package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class ObjectResolver implements Resolver {
	private final Declarations declarations;

	public ObjectResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		String trim = name.trim();
		return Optional.of(new ObjectType(declarations, trim));
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.empty();
	}
}
