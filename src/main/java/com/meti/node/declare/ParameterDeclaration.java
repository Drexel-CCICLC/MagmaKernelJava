package com.meti.node.declare;

import com.meti.node.Type;

import java.util.List;

public class ParameterDeclaration extends AbstractDeclaration {
	public ParameterDeclaration(List<String> stack, Type type) {
		super(stack, type);
	}

	@Override
	public boolean isParameter() {
		return true;
	}
}
