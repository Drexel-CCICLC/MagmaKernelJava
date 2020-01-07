package com.meti;

import java.util.Optional;

public class Type {
	private final String content;
	private final Type returnType;

	public Type(String content) {
		this(content, null);
	}

	public Type(String content, Type returnType) {
		this.content = content;
		this.returnType = returnType;
	}

	public String render() {
		return content;
	}

	public Optional<Type> returnType() {
		return Optional.ofNullable(returnType);
	}
}
