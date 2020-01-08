package com.meti;

import java.util.Optional;

public class ArrayUnit implements Unit {
	@Override
	public boolean canCompile(String value) {
		return value.trim().startsWith("Array");
	}

	@Override
	public String compile(String value, Compiler compiler) {
		String trim = value.trim();
		Type type = parseType(compiler, trim);
		int sizeStart = trim.indexOf('(');
		int sizeEnd = trim.indexOf(')');
		String sizeString = trim.substring(sizeStart + 1, sizeEnd);
		String compiledSize = compiler.compileOnly(sizeString);
		return "malloc(" + compiledSize + "*sizeof(" + type.render() + "))";
	}

	private Type parseType(Compiler compiler, String trim) {
		int typeStart = trim.indexOf('<');
		int typeEnd = trim.indexOf('>');
		String typeString = trim.substring(typeStart + 1, typeEnd);
		return compiler.resolveName(typeString);
	}

	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		if (canCompile(value)) {
			Type type = parseType(compiler, value.trim());
			return Optional.of(new BuildableType(type.render() + "*", type));
		}
		return Optional.empty();
	}
}
