package com.meti.unit;

import com.meti.Compiler;

import java.util.Optional;

public class ReturnUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("return ")) {
			String valueString = input.substring(7).trim();
			String compiledValue = compiler.compile(valueString);
			return Optional.of("return " + compiledValue + ";");
		}
		return Optional.empty();
	}
}
