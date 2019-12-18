package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;

	public DeclareUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		if (equalsIndex == -1) return Optional.empty();
		List<String> flags = Arrays.stream(input.substring(0, equalsIndex).split(" "))
				.filter(value -> !value.isBlank())
				.collect(Collectors.toList());
		String name = flags.get(flags.size() - 1);
		String value = trimmedInput.substring(equalsIndex + 1).trim();
		return (flags.contains("val") || flags.contains("var")) ?
				extractDeclaration(compiler, name, value, flags) :
				Optional.empty();
	}

	Optional<String> extractDeclaration(Compiler compiler, String name, String value, List<String> flags) {
		declarations.define(name, flags);
		String compiledValue = compiler.compile(value);
		return compiledValue.isBlank() ?
				Optional.of("") :
				Optional.of("var " + aliaser.alias(name) + "=" + compiledValue + ";");
	}

}