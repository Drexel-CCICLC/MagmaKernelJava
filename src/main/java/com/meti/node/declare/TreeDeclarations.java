package com.meti.node.declare;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;

public interface TreeDeclarations {
	Declaration absolute(Collection<String> stack);

	Declaration current();

	String currentName();

	Declaration define(Parameter parameter);

	void define(Type type, String name);

	Declaration defineParent(Type type, String name);

	Stack<String> getStack();

	<T> T inStack(String name, Function<String, T> mapper);

	boolean isRoot(Declaration declaration);

	Declaration parent();

	Optional<Declaration> parent(String name);
}
