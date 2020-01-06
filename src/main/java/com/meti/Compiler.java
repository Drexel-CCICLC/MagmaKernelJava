package com.meti;

public class Compiler {
	public Compiler() {
	}

	String compile(String s) {
		String callback = s.isBlank() ? "" : s + ";";
		return "int main(){" + callback + "return 0;}";
	}
}