package com.meti.unit;

import java.util.List;

public interface Declarations {
	void define(String name);

	void define(String name, List<String> flags);

	void delete(String name);

	boolean hasFlag(String name, String flag);

	boolean isDefined(String name);
}
