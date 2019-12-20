package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;

import java.util.Optional;

public class AssignUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		if (equalsIndex == -1) return Optional.empty();
		String name = trimmedInput.substring(0, equalsIndex);
		String value = trimmedInput.substring(equalsIndex + 1).trim();
		return extractAssignment(compiler, name, value);
	}

	Optional<String> extractAssignment(Compiler compiler, String name, String value) {
		String compiledName = compiler.compile(name);
		String compiledValue = compiler.compile(value);
		return Optional.of(compiledName + "=" + compiledValue + ";");
	}
}