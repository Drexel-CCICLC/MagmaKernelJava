package com.meti;

import java.util.Optional;

public interface Unit {
	boolean canCompile(String value);

	String compile(String value, Compiler compiler);

	Optional<Type> resolveName(String value, Compiler compiler);

	Optional<Type> resolveValue(String value, Compiler compiler);
}
