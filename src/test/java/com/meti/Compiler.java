package com.meti;

public class Compiler {
	public Compiler() {
	}

	Node compile(String s) {
		return new Node(Integer.parseInt(s));
	}
}