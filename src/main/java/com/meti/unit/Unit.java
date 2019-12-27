package com.meti.unit;

import com.meti.Compiler;
import com.meti.type.Type;

import java.util.Optional;

public interface Unit {
	Optional<String> parse(String input, Compiler compiler);

	Optional<Type> resolve(String input, Compiler compiler);
}
