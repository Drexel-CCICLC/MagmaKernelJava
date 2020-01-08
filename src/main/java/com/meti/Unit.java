package com.meti;

public interface Unit {
	boolean canCompile(String value);

	String compile(String value, Compiler compiler);
}
