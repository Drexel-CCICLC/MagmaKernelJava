package com.meti;

import java.util.Map;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.ANY;

public interface GenericStruct extends Struct {
	Map<String, Struct> generics();

	@Override
	default Struct merge(Struct root) {
		if (isInstance(root)) {
			return root;
		}
		return root.parent()
				.map(this::merge)
				.orElse(ANY);
	}
}
