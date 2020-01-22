package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.declare.Flag;
import com.meti.node.bracket.struct.GeneratedNodeBuilder;
import com.meti.node.value.compound.variable.FieldNodeBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Declaration {
    List<Declaration> ancestors();

    Collection<Node> buildAssignments(List<? extends Parameter> parameters);

    Optional<Declaration> child(String name);

    Node declareInstance(int paramSize);

	Declaration define(String name, Type type, Set<Flag> flags);

    Declaration define(Parameter parameter);

    boolean hasChildAsParameter(String childName);

    boolean isParameter();

    FieldNodeBuilder lookupFieldOrder(String name, FieldNodeBuilder builder);

    FieldNodeBuilder lookupFieldType(String childName, FieldNodeBuilder builder);

    boolean matches(String name);

    @Deprecated
    Optional<Declaration> parent();

    Node toInstance();

    Parameter toInstancePair();

    Type toObject();

    Node toParameter();

    GeneratedNodeBuilder toStruct(List<? extends Parameter> parameters, Type returnType, Node block);

    Type type();
}
