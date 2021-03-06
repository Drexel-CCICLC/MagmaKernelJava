package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Optional;

public class ObjectResolver implements Resolver {
	private final Declarations declarations;

	public ObjectResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		String trim = content.trim();
		Type type = declarations.relative(trim)
				.map(Declaration::toDefinedStruct)
				.orElse(declarations.toLazyStruct(trim));
		return Optional.of(type);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.empty();
	}
}
