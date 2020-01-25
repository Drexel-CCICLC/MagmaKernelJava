package com.meti.primitive;

import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class IntResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content) {
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
