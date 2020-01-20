package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.value.compound.variable.FieldNodeBuilder;

import java.util.*;

public interface Declaration {
	Collection<Node> buildAssignments(List<? extends Parameter> parameters);

	Optional<Declaration> child(String name);

	Node declareInstance(int paramSize);

	Declaration define(String name, Type type);

	Declaration define(Parameter parameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	FieldNodeBuilder lookupFieldOrder(String name, FieldNodeBuilder builder);

	FieldNodeBuilder lookupFieldType(String childName, FieldNodeBuilder builder);

	boolean matches(String name);

	Optional<Declaration> parent();

	Node toInstance();

	Parameter toInstancePair();

	Node toParameter();

	StructNodeBuilder toStruct(Set<? extends Parameter> parameters, Type returnType, Node block);

	Type type();
}
