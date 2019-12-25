package com.meti.unit;

import com.meti.type.Type;

import java.util.List;
import java.util.Set;
import java.util.Stack;

public interface Declarations {
	Set<String> childrenOf(String... parent);

	void define(Type type, String... name);

	void define(List<String> flags, Type type, String... name);

	void delete(String... name);

	Declaration get(Stack<String> stack, String name);

	boolean hasAnyFlag(String flag, String name);

	boolean hasFlag(String flag, String... name);

	boolean isDefined(String... name);

	boolean isInScope(String... name);

	int order(String name);
}
