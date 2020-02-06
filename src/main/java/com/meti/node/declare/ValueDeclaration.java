package com.meti.node.declare;

import com.meti.node.Type;

import java.util.List;
import java.util.Set;

public class ValueDeclaration extends AbstractDeclaration {
	public ValueDeclaration(List<String> stack, Type type, Set<Flag> flags) {
		super(stack, type, flags);
	}

	@Override
	public boolean isParameter() {
		return false;
	}
}
