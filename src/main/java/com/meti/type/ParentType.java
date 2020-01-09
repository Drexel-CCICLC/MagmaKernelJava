package com.meti.type;

import java.util.Optional;

public interface ParentType extends Type {
	Optional<Type> child();
}
