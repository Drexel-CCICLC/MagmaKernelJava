package com.meti;

import java.util.Optional;

public class ArrayIndexUnit implements CompoundUnit {
	@Override
	public Optional<? extends Type> resolveName(String value, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		if (canCompile(value)) {
			int start = value.indexOf('[');
			String nameString = value.substring(0, start);
			Type type = compiler.resolveValue(nameString);
			if (type instanceof PointerType) {
				return ((ParentType) type).child();
			} else {
				throw new CompileException(type + " is not an array.");
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean canCompile(String value) {
		int start = value.indexOf('[');
		int end = value.indexOf(']');
		return start != -1 && end != -1 && start < end;
	}

	@Override
	public String compile(String value, Compiler compiler) {
		int start = value.indexOf('[');
		int end = value.indexOf(']');
		String nameString = value.substring(0, start);
		String name = compiler.compileOnly(nameString);
		String indexString = value.substring(start + 1, end);
		String index = compiler.compileOnly(indexString);
		return name + "[" + index + "]";
	}
}
