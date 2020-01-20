package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructNodeBuilder;

import java.util.*;

public interface Declaration {
	Collection<Node> buildAssignments(List<String> parameters);

	Optional<Declaration> child(String name);

	OptionalInt childOrder(String name);

	Type childType(String childType);

	Node declareInstance(int paramSize);

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	boolean matches(String name);

	Optional<Declaration> parent();

	Map<String, Type> toInstance(Declarations source);

	Node toInstance();

	StructNodeBuilder toStruct(Map<String, Type> parameters, Type returnType, Node block);

	Node toSuperVariable();

	Type type();
}
