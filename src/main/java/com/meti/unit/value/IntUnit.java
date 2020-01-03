package com.meti.unit.value;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.PrimitiveType;
import com.meti.Type;
import com.meti.unit.Unit;

import java.util.Optional;

public class IntUnit implements Unit {
	@Override
	public Optional<Node> compile(String value, Compiler compiler) {
		return Optional.of(value)
				.filter(this::isInt)
				.map(Integer::parseInt)
				.map(IntNode::new);
	}

	private boolean isInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Optional<Type> resolve(String value, Compiler compiler) {
		return Optional.<Type>of(PrimitiveType.INT)
				.filter(type -> isInt(value));
	}

	public static class IntNode implements Node {
		private final int value;

		public IntNode(int value) {
			this.value = value;
		}

		@Override
		public String render() {
			return String.valueOf(value);
		}
	}
}