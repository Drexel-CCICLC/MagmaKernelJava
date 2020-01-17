package com.meti.node.value.compound.invoke;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class InvocationResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.contains("(") && trim.endsWith(")")) {
			int start = trim.indexOf('(');
			String callerString = trim.substring(0, start);
			Type callerType = compiler.resolveValue(callerString);
			return callerType.returnType();
		}
		return Optional.empty();
	}
}
