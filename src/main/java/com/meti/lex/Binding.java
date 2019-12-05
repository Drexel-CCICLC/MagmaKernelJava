package com.meti.lex;

public class Binding<T> {
	private T value;

	public Binding(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}
}
