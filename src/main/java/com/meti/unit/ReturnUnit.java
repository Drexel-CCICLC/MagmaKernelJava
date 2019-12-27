package com.meti.unit;

import com.meti.Compiler;
import com.meti.type.Type;
import com.meti.type.TypeStack;

import java.util.Optional;

public class ReturnUnit implements Unit {
	private final TypeStack stack;

	public ReturnUnit(Data data) {
		this.stack = data.getTypeStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("return ")) {
			String valueString = trimmedInput.substring(7).trim();
			String compiledValue = compiler.compile(valueString);
			stack.poll();
			return Optional.of("return " + compiledValue + ";");
		}
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}
