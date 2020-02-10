package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.node.struct.type.FunctionType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Optional;

public class BlockResolver implements Resolver {
	private final Declarations declarations;

	public BlockResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("{}"::equals)
				.map(s -> extractSingleton());
	}

	private Type extractSingleton() {
		String singletonName = declarations.currentName();
		Optional<Declaration> singletonOptional = declarations.relative(singletonName + "$");
		if (singletonOptional.isPresent()) {
			return extractFunction(singletonOptional.get());
		} else {
			throw new IllegalStateException("Failed to find singleton: " + singletonName);
		}
	}

	private Type extractFunction(Declaration declaration) {
		Type type = declaration.type();
		if (!(type instanceof FunctionType)) throw new IllegalStateException("Not a function: " + declaration.name());
		FunctionType functionType = (FunctionType) type;
		return functionType.returnType();
	}
}
