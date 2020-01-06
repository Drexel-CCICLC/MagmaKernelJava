package com.meti;

public class EmptyUnit implements Unit {
	@Override
	public boolean canCompile(String trim) {
		return trim.isBlank();
	}

	@Override
	public String compile(String value, Compiler compiler) {
		return value;
	}
}
