package com.meti.node.array;

import com.meti.node.Type;

public abstract class ValueType implements Type {
	@Override
	public boolean isFunctional() {
		return false;
	}
}
