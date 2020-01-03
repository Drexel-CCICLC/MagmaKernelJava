package com.meti.unit.equals.declare;

import com.meti.Type;

public interface Declaration {
	boolean canAssign(Type type);

	boolean isImmutable();

	String render();
}
