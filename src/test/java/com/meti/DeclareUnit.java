package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Declarations declarations;

	public DeclareUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> compile(String value, Compiler compiler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(other -> other.contains("="))
				.map(other -> assemble(other, compiler));
	}

	private Node assemble(String value, Compiler compiler) {
		String flagString = flagString(value);
		String valueString = valueString(value);
		Collection<Flag> flags = flags(flagString);
		String name = name(flagString);
		String trim = valueString.trim();
		Type type = compiler.resolve(trim);
		declarations.declare(type, flags, name, trim);
		return () -> "";
	}

	private String flagString(String value) {
		int equals = value.indexOf('=');
		return value.substring(0, equals);
	}

	private String valueString(String value) {
		int equals = value.indexOf('=');
		return value.substring(equals + 1);
	}

	private Collection<Flag> flags(String flagString) {
		String trim = flagString.trim();
		int lastSpace = trim.lastIndexOf(' ');
		return Arrays.stream(trim.substring(0, lastSpace)
				.split(" "))
				.map(String::toUpperCase)
				.map(Flag::valueOf)
				.collect(Collectors.toSet());
	}

	private String name(String flagString) {
		String trim = flagString.trim();
		return trim.substring(trim.lastIndexOf(' ') + 1);
	}

	@Override
	public Optional<Type> resolve(String value, Compiler compiler) {
		return Optional.empty();
	}
}
