package com.meti.unit;

import com.meti.Compiler;
import com.meti.Counter;
import com.meti.exception.AlreadyExistsException;

import java.util.Optional;
import java.util.Set;

public class DeclareUnit implements Unit {
	private final Counter counter;
	private final Set<String> declarations;

	public DeclareUnit(Counter counter, Set<String> declarations) {
		this.counter = counter;
		this.declarations = declarations;
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		return trimmedInput.startsWith("val") && equalsIndex != -1 ?
				Optional.of(extractDeclaration(trimmedInput, equalsIndex)) :
				Optional.empty();
	}

	String extractDeclaration(String trimmedInput, int equalsIndex) {
		String name = trimmedInput.substring(4, equalsIndex).trim();
		if (!declarations.add(name)) {
			throw new AlreadyExistsException("\"" + name + "\" has already been defined.");
		}
		return "var " + counter.next() + "=" + trimmedInput.substring(equalsIndex + 1).trim() + ";";
	}
}