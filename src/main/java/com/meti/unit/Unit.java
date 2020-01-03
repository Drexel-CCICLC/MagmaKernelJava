package com.meti.unit;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Type;

import java.util.Optional;

public interface Unit {
	Optional<Node> compile(String value, Compiler compiler);

	Optional<Type> resolve(String value, Compiler compiler);
}
