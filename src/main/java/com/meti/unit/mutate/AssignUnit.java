package com.meti.unit.mutate;

import com.meti.compile.Compiler;
import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class AssignUnit implements CompoundUnit {
	@Override
	public boolean canCompile(String value) {
		return value.contains("=");
	}

	@Override
	public String compile(String value, ComplexCompiler compiler) {
		String first = parseFirst(value, compiler);
		String last = parseLast(value, compiler);
		return first + "=" + last + ";";
	}

	private String parseFirst(String value, Compiler compiler) {
		int equalsIndex = index(value);
		String firstString = value.substring(0, equalsIndex);
		return compiler.compileOnly(firstString);
	}

	private int index(String value) {
		return value.indexOf('=');
	}

	private String parseLast(String value, Compiler compiler) {
		int equalsIndex = index(value);
		String lastString = value.substring(equalsIndex + 1);
		return compiler.compileOnly(lastString);
	}

	@Override
	public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
		return Optional.empty();
	}
}
