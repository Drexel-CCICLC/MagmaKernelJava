package com.meti;

import java.util.Optional;

public interface Unit {
	Optional<Node> compile(String value, Compiler compiler);

	Optional<Type> resolve(String value, Compiler compiler);
}
