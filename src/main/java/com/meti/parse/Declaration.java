package com.meti.parse;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Declaration {
	List<Declaration> children();

	void clear();

	Node declareInstance();

	Declaration define(Type type, String name, Set<Flag> flags);

	Declaration define(Parameter parameter);

	default boolean hasChild(String trim) {
		return child(trim).isPresent();
	}

	Optional<Declaration> child(String name);

	boolean hasParameter(String childName);

	String instanceName();

	boolean isClass();

	boolean isFunctional();

	boolean isNative();

	boolean isParameter();

	boolean isSuperStructure();

	String joinArgs();

	String joinStack();

	boolean matches(String name);

	String name();

	Type toDefinedStruct();

	Parameter toParameter();

	List<Node> toParentParameters();

	Node toStruct();

	Node toVariable();

	Type type();
}
