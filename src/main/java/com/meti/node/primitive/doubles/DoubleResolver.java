package com.meti.node.primitive.doubles;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class DoubleResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter("Double"::equals)
				.map(s -> DoubleType.INSTANCE);
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		String trim = content.trim();
		String doubleValue = trim.endsWith("d") ?
				trim.substring(0, trim.length() - 1) :
				trim;
		try {
			Double.parseDouble(doubleValue);
			return Optional.of(DoubleType.INSTANCE);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
