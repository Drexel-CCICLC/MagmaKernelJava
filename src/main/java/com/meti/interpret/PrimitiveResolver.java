package com.meti.interpret;

import com.meti.assemble.BooleanNode;
import com.meti.assemble.IntNode;
import com.meti.assemble.Node;
import com.meti.assemble.StringNode;

import java.util.Arrays;
import java.util.Optional;

import static com.meti.interpret.PrimitiveType.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

class PrimitiveResolver implements Resolver {
	@Override
	public Optional<Type> resolve(Node node) {
		if (node instanceof IntNode) {
			return of(INT);
		} else if (node instanceof StringNode) {
			return of(STRING);
		} else if (node instanceof BooleanNode) {
            return of(BOOLEAN);
		} else {
			return empty();
		}
	}

	@Override
	public Optional<? extends Type> resolve(String value) {
		return Arrays.stream(values())
				.filter(primitiveType -> primitiveType.value().equals(value))
				.findAny();
	}
}
