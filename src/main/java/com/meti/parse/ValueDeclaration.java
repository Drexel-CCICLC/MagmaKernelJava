package com.meti.parse;

import com.meti.node.Type;

import java.util.List;
import java.util.Set;

public class ValueDeclaration extends AbstractDeclaration {
	public ValueDeclaration(List<String> stack, Type type, Set<Flag> flags) {
		super(stack, flags, type);
	}

	@Override
	public boolean isParameter() {
		return false;
	}
}
