package com.meti;

import java.util.Optional;

public class IntUnit implements Unit {
	@Override
	public Optional<Node> assemble(String value, Assembler assembler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(this::isInt)
				.map(IdentityNode::new);
	}

	@Override
	public Optional<Type> resolveName(String name, Assembler assembler) {
		return Optional.of(name)
				.map(String::trim)
				.filter(value -> value.equals("int"))
				.map(value -> PrimitiveType.INT);
	}

	@Override
	public Optional<Type> resolveValue(String value, Assembler assembler) {
		return Optional.of(value)
				.map(String::trim)
				.filter(this::isInt)
				.map(other -> PrimitiveType.INT);
	}

	private boolean isInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
