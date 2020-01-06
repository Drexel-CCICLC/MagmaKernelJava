package com.meti;

import java.util.Optional;

public interface Unit {
	boolean canCompile(String value);

	String compile(String value, Compiler compiler);

	Optional<String> resolveName(String value, Compiler compiler);

	Optional<String> resolveValue(String value, Compiler compiler);
}
