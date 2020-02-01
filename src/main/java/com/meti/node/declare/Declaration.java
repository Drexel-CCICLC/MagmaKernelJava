package com.meti.node.declare;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.List;
import java.util.Optional;

public interface Declaration {
	Optional<Declaration> child(String name);

	List<Declaration> children();

	Node declareInstance();

	Declaration define(Type type, String name);

	Declaration define(Parameter parameter);

	String instanceName();

	boolean isFunction();

	boolean isParent();

	String joinArgs();

	boolean matches(String name);

	String name();

	String tempName();

	Parameter toParameter();

	Node toStruct();

	Node toVariable();

	Type type();
}
