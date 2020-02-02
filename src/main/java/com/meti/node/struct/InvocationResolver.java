package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.Optional;

public class InvocationResolver implements Resolver {
	private final Declarations declarations;

	public InvocationResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.endsWith(")")) {
			int start = trim.indexOf('(');
			String caller = trim.substring(0, start);
			Optional<Declaration> optional = declarations.relative(caller);
			if (optional.isPresent()) {
				Declaration declaration = optional.get();
				if (declaration.isFunctional()) {
					FunctionType castedType = (FunctionType) declaration.type();
					Type returnType = castedType.returnType();
					return Optional.of(returnType);
				}
			}
		}
		return Optional.empty();
	}
}