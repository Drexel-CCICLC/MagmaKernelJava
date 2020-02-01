package com.meti.node.declare;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.struct.StructNode;
import com.meti.node.struct.StructType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Declaration {
    Optional<Declaration> child(String name);

    default Node declareInstance() {
        return new DeclareNode(new StructType(getName()),
                tempName(), new VariableNode("{" + joinArgs() + "}"));
    }

    String getName();

    default String tempName() {
        return getName() + "_";
    }

    default String joinArgs() {
        return childrenAsParams().stream()
                .map(Parameter::name)
                .collect(Collectors.joining(","));
    }

    default List<Parameter> childrenAsParams() {
        return children()
                .stream()
                .filter(child -> !child.isFunction())
                .map(Declaration::toParameter)
                .collect(Collectors.toList());
    }

    List<Declaration> children();

    Parameter toParameter();

    Declaration define(Type type, String name);

    Declaration define(Parameter parameter);

    String instanceName();

    boolean isFunction();

    boolean isParent();

    default Node toStruct() {
        return new StructNode(getName(), childrenAsParams());
    }
}
