package com.meti.character;

import com.meti.Compiler;
import com.meti.ParseException;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class CharResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		if ("Char".equals(name.trim())) return Optional.of(new CharType());
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		String trim = value.trim();
		if (trim.startsWith("'") && trim.endsWith("'")) {
			String charString = trim.substring(1, trim.length() - 1);
			if (1 == charString.length()) {
				return Optional.of(new CharType());
			} else {
				throw new ParseException("Too many characters.");
			}
		}
		return Optional.empty();
	}
}
