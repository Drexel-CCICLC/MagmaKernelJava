package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareUnit implements Unit {
	private final Declarations declarations;

	public DeclareUnit(Declarations declarations) {
		this.declarations = declarations;
	}

	@Override
	public Optional<Node> assemble(String value, Assembler assembler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(this::hasEquals)
				.map(other -> build(other, assembler));
	}

	private boolean hasEquals(String value) {
		return value.contains("=");
	}

	private Node build(String string, Assembler assembler) {
		String args = args(string);
		String value = value(string);
		Collection<Flag> flags = flags(args);
		String name = name(args);
		Type type = assembler.resolveValue(value);
		Declaration declaration = declarations.define(type, flags, name, value);
		return new Node() {
			@Override
			public Optional<Declaration> declaration() {
				return Optional.empty();
			}

			@Override
			public String render() {
				return "";
			}
		};
	}

	private String args(String value) {
		int equalsIndex = value.indexOf('=');
		return value.substring(0, equalsIndex);
	}

	private String value(String value) {
		int equalsIndex = value.indexOf('=');
		return value.substring(equalsIndex + 1);
	}

	private List<Flag> flags(String value) {
		String[] flagStrings = value.substring(0, value.lastIndexOf(' '))
				.split(" ");
		return Arrays.stream(flagStrings)
				.map(String::toUpperCase)
				.map(Flag::valueOf)
				.collect(Collectors.toList());
	}

	private String name(String value) {
		return value.substring(value.lastIndexOf(' ') + 1);
	}

	@Override
	public Optional<Type> resolveName(String name, Assembler assembler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Assembler assembler) {
		return Optional.empty();
	}
}
