package com.meti;

import java.util.Optional;

public abstract class PrimitiveType implements Type {
	@Override
	public boolean isNamed() {
		return false;
	}

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
