package com.meti.node.declare;

import com.meti.node.Type;

import static java.util.Collections.singletonList;

public class ValueDeclaration extends AbstractDeclaration {
	public ValueDeclaration(String name, Type type) {
		super(singletonList(name), type);
	}
}
