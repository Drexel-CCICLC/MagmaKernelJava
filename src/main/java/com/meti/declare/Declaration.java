package com.meti.declare;

import com.meti.node.Type;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Type> childMap();

	default OptionalInt childOrder(String name) {
		String[] childArray = childMap().keySet().toArray(String[]::new);
		return IntStream.range(0, childArray.length)
				.filter(i -> childArray[i].equals(name))
				.findFirst();
	}

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	String name();

	Optional<Declaration> parent();

	Type type();
}
