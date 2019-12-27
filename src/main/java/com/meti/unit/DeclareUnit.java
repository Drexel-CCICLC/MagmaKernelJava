package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Compiler;
import com.meti.Declarations;
import com.meti.exception.CompileException;
import com.meti.exception.TypeException;
import com.meti.run.Compile;
import com.meti.type.PrimitiveType;
import com.meti.type.Type;
import com.meti.type.TypeStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Aliaser aliaser;
	private final Declarations manager;
	private final TypeStack typeStack;

	public DeclareUnit(Data data) {
		this.typeStack = data.getTypeStack();
		this.manager = data.getManager();
		this.aliaser = data.getAliaser();
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

	Optional<String> extractDeclaration(Compiler compiler, String name, String value, Collection<String> flags) {
		manager.defineTemp(name, flags);
		String result = extractValue(compiler, name, value);
		Type type = typeStack.poll();
		if(type.equals(PrimitiveType.VOID)) {
			throw new TypeException("Cannot declare value as void type.");
		}
		Declaration declaration = manager.define(name, type, flags);
		return declaration.isNative() ?
				Optional.of("") :
				Optional.of("var " + aliaser.alias(name) + "=" + result + ";");
	}

	private String extractValue(Compiler compiler, String name, String value) {
		manager.sink(name);
		String result = compiler.compile(value);
		manager.surface();
		return result;
	}

	@Override
	public Optional<Type> resolve(String input, Compiler compiler) {
		return Optional.empty();
	}
}