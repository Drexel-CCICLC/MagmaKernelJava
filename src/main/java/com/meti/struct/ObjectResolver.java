package com.meti.struct;

import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.Resolver;
import com.meti.Type;

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
