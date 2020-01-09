package com.meti.unit;

import com.meti.compile.Compiler;

public interface Unit {
	boolean canCompile(String value);

	String compile(String value, Compiler compiler);
}
