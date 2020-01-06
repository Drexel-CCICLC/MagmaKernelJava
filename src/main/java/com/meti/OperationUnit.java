package com.meti;

public class OperationUnit implements Unit {
	@Override
	public boolean canCompile(String trim) {
		return trim.contains("+");
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		int operation = trim.indexOf('+');
		String first = trim.substring(0, operation);
		String last = trim.substring(operation + 1);
		return compiler.compileOnly(first) + "+" + compiler.compileOnly(last);
	}
}
