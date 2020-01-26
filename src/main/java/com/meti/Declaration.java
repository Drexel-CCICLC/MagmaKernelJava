package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Declaration {
	default AssignNode assignField() {
		return new AssignNode(new VariableNode(instanceName()),
				new VariableNode("{" + joinArgs() + "}"));
	}

	Optional<Declaration> child(String name);

	List<Declaration> children();

	default List<Parameter> childrenAsParams() {
		return children()
				.stream()
				.map(Declaration::toParameter)
				.collect(Collectors.toList());
	}

	Declaration define(Type type, String name);

	Declaration define(Parameter parameter);

	String getName();

	Type getType();

	default String instanceName() {
		return getName() + "_";
	}

	boolean isParent();

	default String joinArgs() {
		return childrenAsParams().stream()
				.map(Parameter::name)
				.collect(Collectors.joining(","));
	}

	Parameter toParameter();

	default StructNode toStruct() {
		return new StructNode(getName(), childrenAsParams());
	}
}
