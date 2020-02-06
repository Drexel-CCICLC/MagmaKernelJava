package com.meti.parse;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Declarations {
	Declaration absolute(Collection<String> stack);

	String buildStackName();

	List<Parameter> buildStackParameters();

	void clear();

	Declaration current();

	String currentName();

	void define(Parameter parameter);

	void define(Type type, String name, Set<Flag> flags);

	void defineParent(Type type, String name, Set<Flag> flags);

	Set<Flag> flags();

	<T> T inStack(String name, Function<? super String, T> mapper);

	boolean isInClass();

	boolean isInSingleton();

	boolean isParent(Declaration parent);

	boolean isRoot(Declaration declaration);

	Declaration parent();

	Optional<Declaration> parent(String name);

	Optional<Declaration> relative(String name);

	Type toCurrentClass(String name);

	Type toCurrentClass();
}
