package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.exception.AlreadyExistsException;

import java.util.Optional;
import java.util.Set;

public class DeclareUnit implements Unit {
	private final Aliaser aliaser;
	private final Set<String> declarations;

	public DeclareUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		return trimmedInput.startsWith("val") && equalsIndex != -1 ?
				Optional.of(extractDeclaration(trimmedInput, equalsIndex, compiler)) :
				Optional.empty();
	}

	String extractDeclaration(String trimmedInput, int equalsIndex, Compiler compiler) {
		String name = trimmedInput.substring(4, equalsIndex).trim();
		if (!declarations.add(name)) {
			throw new AlreadyExistsException("\"" + name + "\" has already been defined.");
		}
		return "var " + aliaser.alias(name) + "=" + compiler.compile(trimmedInput.substring(equalsIndex + 1).trim()) + ";";
	}
}