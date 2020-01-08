package com.meti;

import java.util.Optional;

public interface ParentType extends Type {
	Optional<Type> child();

	boolean isVariable();
}
