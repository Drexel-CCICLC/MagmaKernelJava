package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Declaration {
    default Node declareInstance() {
        return new DeclareNode(new StructType(getName()),
                tempName(),  new VariableNode("{" + joinArgs() + "}"));
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

    default String tempName() {
		return getName() + "_";
    }

    default String instanceName() {
        return getName() + "$";
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
