package com.meti;

import java.util.Collection;

public interface Declarations {
	Declaration absolute(Collection<String> frames);

	Declaration current();

	default Declaration define(Type type, Collection<Flag> flags, String name) {
		return define(type, flags, name, null);
	}

	Declaration define(Type type, Collection<Flag> flags, String name, String value);

	Declaration scope(String name);

	void sink(String frame);

	void surface();
}
