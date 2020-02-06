package com.meti.parse;

import com.meti.node.Type;

import java.util.Collections;
import java.util.List;

public class ParameterDeclaration extends AbstractDeclaration {
	public ParameterDeclaration(List<String> stack, Type type) {
		super(stack, Collections.emptySet(), type);
	}

	@Override
	public boolean isParameter() {
		return true;
	}
}
