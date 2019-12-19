package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final Stack<String> stack;

	public DeclareUnit(Data data) {
		this.aliaser = data.getAliaser();
		this.declarations = data.getDeclarations();
		this.stack = data.getStack();
	}

	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		int equalsIndex = trimmedInput.indexOf('=');
		if (equalsIndex == -1) return Optional.empty();
		List<String> flags = Arrays.stream(trimmedInput.substring(0, equalsIndex).split(" "))
				.filter(value -> !value.isBlank())
				.collect(Collectors.toList());
		String name = flags.get(flags.size() - 1).trim();
		String value = trimmedInput.substring(equalsIndex + 1).trim();
		return (flags.contains("val") || flags.contains("var")) ?
				extractDeclaration(compiler, name, value, flags) :
				Optional.empty();
	}

	Optional<String> extractDeclaration(Compiler compiler, String name, String value, List<String> flags) {
		stack.push(name);
		declarations.define(flags, stack.toArray(String[]::new));
		String result = compiler.compile(value);
		Optional<String> toReturn = flags.contains("native") ?
				Optional.of("") :
				Optional.of("var " + aliaser.alias(name) + "=" + result + ";");
		stack.pop();
		return toReturn;
	}

}