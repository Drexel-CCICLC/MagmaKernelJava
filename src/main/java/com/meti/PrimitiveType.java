package com.meti;

import com.meti.Type;

public abstract class PrimitiveType implements Type {
	@Override
	public boolean isNamed() {
		return false;
	}
}
