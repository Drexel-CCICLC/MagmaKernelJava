package com.meti;

import java.util.Optional;
import java.util.OptionalInt;

public abstract class PrimitiveType implements Type {
	@Override
	public OptionalInt childOrder(String name) {
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String name) {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		return false;
	}

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
