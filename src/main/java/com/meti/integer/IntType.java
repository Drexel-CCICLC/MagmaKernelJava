package com.meti.integer;

import com.meti.Type;

import java.util.Optional;

public class IntType implements Type {
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
