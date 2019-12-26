package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.type.PrimitiveType;
import com.meti.type.TypeStack;
import com.meti.unit.Data;
import com.meti.unit.Unit;

import java.util.Optional;

public class StringUnit implements Unit {
	private final TypeStack typeStack;

	public StringUnit(Data data) {
		this.typeStack = data.getTypeStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if(trimmedInput.startsWith("\"") && trimmedInput.endsWith("\"")) {
			typeStack.add(PrimitiveType.STRING);
			return Optional.of(trimmedInput);
		}
		return Optional.empty();
	}
}
