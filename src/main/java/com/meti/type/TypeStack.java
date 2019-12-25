package com.meti.type;

public interface TypeStack {
	void add(Type type);

	Type poll();

	void merge();
}
