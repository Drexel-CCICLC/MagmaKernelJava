package com.meti.node.declare;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Declarations {
	Declaration absolute(Collection<String> stack);

	String buildStackName();

	List<Parameter> buildStackParameters();

	Declaration current();

	String currentName();

	Declaration define(Parameter parameter);

	void define(Type type, String name);

	Declaration defineParent(Type type, String name);

	<T> T inStack(String name, Function<? super String, T> mapper);

	boolean isRoot(Declaration declaration);

	Declaration parent();

	Optional<Declaration> parent(String name);
}
