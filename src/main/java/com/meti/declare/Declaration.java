package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructNodeBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public interface Declaration {
	Node buildSuperAssignment(Compiler compiler, int index, String paramName);

	Collection<Node> buildSuperConstructors(Compiler compiler, Map<String, Type> parameters, Node size);

	Optional<Declaration> child(String name);

	Map<String, Type> childMap();

	OptionalInt childOrder(String name);

	Type childType(String childType);

	StructNodeBuilder createStructBuilder();

	Node declareInstance(Compiler compiler, Map<String, Type> parameters);

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	Optional<Declaration> parent();

	Map<String, Type> toInstance(Declarations source);

	Node toInstance();

	Node toSuperVariable();

	Type type();
}
