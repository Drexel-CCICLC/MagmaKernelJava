package com.meti;

public class EmptyNode implements Node {
	private static final String EMPTY_CONTENT = "";

	@Override
	public String render() {
		return EMPTY_CONTENT;
	}
}
