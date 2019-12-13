package com.meti;

import java.util.Optional;

public class PrimitiveNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		return Optional.of(new PrimitiveNode(PrimitiveStruct.INT, Integer.parseInt(value)));
	}

	public enum PrimitiveStruct implements Struct {
		CHAR,
		INT,
		ANY;

		@Override
		public boolean isInstance(Struct other) {
			return this == other;
		}

		@Override
		public Optional<Struct> parent() {
			return Optional.empty();
		}
	}

	public static class PrimitiveNode extends AbstractNode {
		private final int value;

		public PrimitiveNode(Struct struct, int value) {
			super(struct);
			this.value = value;
		}

		@Override
		public String compile(Aliaser aliaser) {
			return String.valueOf(value);
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
