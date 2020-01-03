package com.meti.unit.equals.declare;

import com.meti.Type;

import java.util.Collection;

public interface Declarations {
	Declaration declare(Type type, Collection<Flag> flags, String name, String value);
}
