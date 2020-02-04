package com.meti.node;

public abstract class ValueType implements Type {
	@Override
	public boolean isFunctional() {
		return false;
	}
}
