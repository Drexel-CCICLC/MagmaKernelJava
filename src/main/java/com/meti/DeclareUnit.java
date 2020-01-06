package com.meti;

public class DeclareUnit implements Unit {
	@Override
	public boolean canCompile(String trim) {
		return trim.startsWith("val");
	}

	@Override
	public String compile(String trim, Compiler compiler) {
		int index = trim.indexOf('=');
		String keys = trim.substring(0, index).trim();
		int lastSpace = keys.lastIndexOf(' ');
		String name = keys.substring(lastSpace + 1);
		String value = trim.substring(index + 1);
		return "int " + name + "$=" + compiler.compileOnly(value) + ";int *" + name + "=&" + name + "$;";
	}
}
