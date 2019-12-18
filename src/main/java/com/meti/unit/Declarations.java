package com.meti.unit;

import java.util.List;

public interface Declarations {
	void define(String... name);

	void define(List<String> flags, String... name);

	void delete(String... name);

	boolean hasFlag(String flag, String... name);

	boolean isDefined(String... name);
}
