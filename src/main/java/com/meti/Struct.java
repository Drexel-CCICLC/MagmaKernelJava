package com.meti;

import java.util.Optional;

public interface Struct {
	boolean isInstance(Struct other);

	Struct merge(Struct root);

	String name();

	Optional<Struct> parent();

	Optional<Node> parentNode();
}
