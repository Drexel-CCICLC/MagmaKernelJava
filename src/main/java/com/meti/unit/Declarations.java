package com.meti.unit;

import java.util.List;
import java.util.Set;

public interface Declarations {
	Set<String> childrenOf(String... parent);

	void define(String... name);

	void define(List<String> flags, String... name);

	void delete(String... name);

	boolean hasFlag(String flag, String... name);

	boolean hasAnyFlag(String flag, String name);

	boolean isDefined(String... name);

	boolean isInScope(String... name);

	int order(String name);
}
