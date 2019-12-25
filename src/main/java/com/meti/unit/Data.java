package com.meti.unit;

import com.meti.Aliaser;
import com.meti.type.TypeStack;

import java.util.Stack;

public interface Data {
	Stack<String> getStack();

	Aliaser getAliaser();

	TypeStack getTypeStack();

	Declarations getDeclarations();
}
