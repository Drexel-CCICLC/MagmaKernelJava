package com.meti.node.declare;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Declaration {
	Optional<Declaration> child(String name);

	List<Declaration> children();

	Node declareInstance();

	Declaration define(Type type, String name, Set<Flag> flags);

	Declaration define(Parameter parameter);

	String instanceName();

	boolean isFunctional();

	boolean isNative();

	boolean isParameter();

	boolean isParent();

	String joinArgs();

	String joinStack();

	boolean matches(String name);

	String name();

	String tempName();

	Parameter toParameter();

	List<Node> toParentParameters();

	Node toStruct();

	Node toVariable();

	Type type();
}
