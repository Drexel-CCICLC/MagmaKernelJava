package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.value.compound.variable.FieldNodeBuilder;

import java.util.*;

public interface Declaration {
	Collection<Node> buildAssignments(List<Parameter> parameters);

	Optional<Declaration> child(String name);

	Node declareInstance(int paramSize);

	void define(String name, Type type);

	void define(Parameter parameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	FieldNodeBuilder lookupFieldOrder(String name, FieldNodeBuilder builder);

	FieldNodeBuilder lookupFieldType(FieldNodeBuilder builder, String childName);

	boolean matches(String name);

	OptionalInt orderOf(String name);

	Optional<Declaration> parent();

	Parameter toInstancePair();

	Node toInstanceParameter();

	Node toParameter();

	StructNodeBuilder toStruct(Set<? extends Parameter> parameters, Type returnType, Node block);

	Type type();
}
