package com.meti;

public class Node {
	private final int value;

	public Node(int value) {
		this.value = value;
	}

	protected String render() {
		return String.valueOf(value);
	}
}