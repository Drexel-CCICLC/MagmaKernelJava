package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.StructNodeBuilder;

import java.util.*;

public interface Declaration {
	Collection<Node> buildSuperConstructors(Node size, List<String> copy);

	Optional<Declaration> child(String name);

	OptionalInt childOrder(String name);

	Type childType(String childType);

	StructNodeBuilder createStructBuilder();

	Node declareInstance(Compiler compiler, int paramSize);

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	int index();

	boolean isParameter();

	boolean matches(String name);

	Optional<Declaration> parent();

	Map<String, Type> toInstance(Declarations source);

	Node toInstance();

	Node toSuperVariable();

	Type type();
}
