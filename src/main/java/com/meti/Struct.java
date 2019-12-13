package com.meti;

import java.util.Optional;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.ANY;

public interface Struct {
	boolean isInstance(Struct other);

	default Struct merge(Struct root) {
		if (isInstance(root)) {
			return root;
		}
		return root.parent()
				.map(this::merge)
				.orElse(ANY);
	}

	String name();

	Optional<Struct> parent();
}
