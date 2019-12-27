package com.meti.unit;

import com.meti.exception.TypeException;
import com.meti.type.Type;
import com.meti.type.TypeStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ListTypeStack implements TypeStack {
	private final Stack<Type> types = new Stack<>();

	@Override
	public void add(Type type) {
		types.add(type);
	}

	@Override
	public Type poll() {
		if (types.isEmpty()) {
			throw new TypeException("Could not resolve type.");
		} else {
			return types.pop();
		}
	}

	@Override
	public void merge() {
		throw new UnsupportedOperationException();
	}
}
