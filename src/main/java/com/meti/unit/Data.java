package com.meti.unit;

import com.meti.Aliaser;
import com.meti.DeclareManager;
import com.meti.type.TypeStack;
import com.meti.unit.bracket.DeclareStack;

import java.util.Stack;

public interface Data {
	@Deprecated
	DeclareStack getDeclareStack();

	DeclareManager getManager();

	@Deprecated
	Stack<String> getStack();

	Aliaser getAliaser();

	TypeStack getTypeStack();

	@Deprecated
	Declarations getDeclarations();
}
