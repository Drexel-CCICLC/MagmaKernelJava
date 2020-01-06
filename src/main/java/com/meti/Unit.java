package com.meti;

public interface Unit {
	boolean canCompile(String trim);

	String compile(String trim, Compiler compiler);
}
