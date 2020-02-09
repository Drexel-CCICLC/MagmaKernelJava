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
		try {
			String doubleValue = clip(content);
			Double.parseDouble(doubleValue);
			return Optional.of(DoubleType.INSTANCE);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private String clip(String content) {
		String trim = content.trim();
		return trim.endsWith("d") ?
				trim.substring(0, trim.length() - 1) :
				trim;
	}
}
