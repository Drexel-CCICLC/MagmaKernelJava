package com.meti;

import java.util.Optional;

public class ArrayIndexUnit implements CompoundUnit {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return Optional.of(value)
				.filter(this::canCompile)
				.flatMap(s -> parse(s, compiler));
	}

	@Override
	public boolean canCompile(String value) {
		int start = startOfIndex(value);
		int end = endOfIndex(value);
		return start != -1 && end != -1 && start < end;
	}

	private Optional<Type> parse(String value, Compiler compiler) {
		String name = parseName(value);
		return resolveType(name, compiler);
	}

	private int startOfIndex(String value) {
		return value.indexOf('[');
	}

	private int endOfIndex(String value) {
		return value.indexOf(']');
	}

	private String parseName(String value) {
		int start = startOfIndex(value);
		return value.substring(0, start);
	}

	private Optional<Type> resolveType(String name, Compiler compiler) {
		Type type = compiler.resolveValue(name);
		if (type instanceof PointerType) return resolveChild(type);
		throw new CompileException(type + " is not an array.");
	}

	private Optional<Type> resolveChild(Type type) {
		ParentType parent = (ParentType) type;
		return parent.child();
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String name = parseName(value, compiler);
		String index = parseIndex(value, compiler);
		return name + "[" + index + "]";
	}

	private String parseName(String value, Compiler compiler) {
		int start = startOfIndex(value);
		String nameString = value.substring(0, start);
		return compiler.compileOnly(nameString);
	}

	private String parseIndex(String value, Compiler compiler) {
		int start = startOfIndex(value);
		int end = endOfIndex(value);
		String indexString = value.substring(start + 1, end);
		return compiler.compileOnly(indexString);
	}
}
