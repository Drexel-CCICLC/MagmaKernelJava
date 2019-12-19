package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.unit.Unit;

import java.util.Optional;
import java.util.Set;

public class OperationUnit implements Unit {
	private final Set<Character> operations = Set.of(
			'+',
			'-',
			'*',
			'/',
			'%',
			'<'
	);

	private boolean hasChar(Character character, String input) {
		return input.indexOf(character) != -1;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		Optional<Integer> operation = operations.stream()
				.map(input::indexOf)
				.filter(integer -> integer != -1)
				.findAny();
		if (operation.isPresent()) {
			int operationIndex = operation.get();
			String value0 = input.substring(0, operationIndex);
			String value1 = input.substring(operationIndex + 1);
			String compile0 = compiler.compile(value0);
			String compile1 = compiler.compile(value1);
			return Optional.of(compile0 + input.charAt(operationIndex) + compile1);
		}
		return Optional.empty();
	}
}
