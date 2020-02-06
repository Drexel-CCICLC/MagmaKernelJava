package com.meti.node.declare;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.exception.ParseException;
import com.meti.node.Type;
import com.meti.node.struct.ObjectType;

import java.util.Optional;

public class VariableResolver implements Resolver {
	private final Declarations declarations;

	public VariableResolver(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		if (trim.contains(".")) {
			int period = trim.indexOf('.');
			String before = trim.substring(0, period);
			String after = trim.substring(period + 1);
			Type type = compiler.resolveValue(before);
			if (type instanceof ObjectType) {
				return ((ObjectType) type).declaration()
						.child(after)
						.map(Declaration::type);
			} else {
				throw new ParseException(before + " is not an object.");
			}
		} else {
			String singletonName = trim + "$";
			Optional<Declaration> singleton = declarations.relative(singletonName);
			if (singleton.isPresent()) {
				Declaration declaration = singleton.get();
				return declaration.child(trim)
						.map(Declaration::type);
			} else {
				return declarations.relative(trim)
						.map(Declaration::type);
			}
		}
	}
}
