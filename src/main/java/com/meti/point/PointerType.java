package com.meti.point;

import com.meti.Type;

import java.util.Optional;

public class PointerType implements Type {
	private final Type child;

	public PointerType(Type child) {
		this.child = child;
	}

	@Override
	public boolean isNamed() {
		return child.isNamed();
	}

	@Override
	public String render() {
		//TODO: rule changes with functions?
		return child.render() + "*";
	}

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
