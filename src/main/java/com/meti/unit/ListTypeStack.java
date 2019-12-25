package com.meti.unit;

import com.meti.exception.TypeException;
import com.meti.type.Type;
import com.meti.type.TypeStack;

import java.util.LinkedList;

public class ListTypeStack implements TypeStack {
	private final LinkedList<Type> types = new LinkedList<>();

	@Override
	public void add(Type type) {
		types.add(type);
	}

	@Override
	public Type poll() {
		if (types.isEmpty()) {
			throw new TypeException("Could not resolve type.");
		} else {
			return types.poll();
		}
	}

	@Override
	public void merge() {
		throw new UnsupportedOperationException();
	}
}
