package com.meti.node.primitive;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class IntResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.equals("Int"))
				.map(s -> IntType.INSTANCE);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		String intValue = trim.endsWith("i") ?
				trim.substring(0, trim.length() - 1) :
				trim;
		try {
			Integer.parseInt(intValue);
			return Optional.of(IntType.INSTANCE);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
