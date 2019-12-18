package com.meti.unit;

import com.meti.Compiler;

import java.util.Optional;

public class ReturnUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("return ")) {
			String valueString = trimmedInput.substring(7).trim();
			String compiledValue = compiler.compile(valueString);
			return Optional.of("return " + compiledValue + ";");
		}
		return Optional.empty();
	}
}
