package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.unit.Unit;

import java.util.Optional;
import java.util.Set;

public class OperationUnit implements Unit {
	private final Set<String> operations = Set.of(
			"+",
			"-",
			"*",
			"/",
			"%",
			"<",
			"=="
	);

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		Optional<String> optional = operations.stream()
				.filter(input::contains)
				.findAny();
		if (optional.isPresent()) {
			String operation = optional.get();
			int operationIndex = input.indexOf(operation);
			String value0 = input.substring(0, operationIndex);
			String value1 = input.substring(operationIndex + operation.length());
			String compile0 = compiler.compile(value0);
			String compile1 = compiler.compile(value1);
			if(operation.equals("==")) operation = "===";
			return Optional.of(compile0 + operation + compile1);
		}
		return Optional.empty();
	}
}
