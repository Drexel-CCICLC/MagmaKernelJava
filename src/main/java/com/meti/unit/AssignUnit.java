package com.meti.unit;

import com.meti.Compiler;
import com.meti.exception.ImmutableException;

import java.util.Optional;

public class AssignUnit implements Unit {
	private final Data data;

	public AssignUnit(Data data) {
		this.data = data;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		if (equalsIndex == -1) return Optional.empty();
		if (trimmedInput.charAt(equalsIndex + 1) == '=') return Optional.empty();
		String name = trimmedInput.substring(0, equalsIndex);
		String value = trimmedInput.substring(equalsIndex + 1).trim();
		return extractAssignment(compiler, name, value);
	}

	Optional<String> extractAssignment(Compiler compiler, String name, String value) {
		if (data.getDeclarations().hasAnyFlag("val", name.trim())) throw new ImmutableException(name + " is immutable.");
		String compiledName = compiler.compile(name);
		String compiledValue = compiler.compile(value);
		return Optional.of(compiledName + "=" + compiledValue + ";");
	}
}