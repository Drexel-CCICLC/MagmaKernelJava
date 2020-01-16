package com.meti.integer;

import com.meti.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class IntType implements Type {
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
	public String render() {
		return "int";
	}

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
