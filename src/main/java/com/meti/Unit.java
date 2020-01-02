package com.meti;

import java.util.Optional;

public interface Unit {
	Optional<Node> assemble(String value, Assembler assembler);

	Optional<Type> resolveName(String name, Assembler assembler);

	Optional<Type> resolveValue(String value, Assembler assembler);
}
