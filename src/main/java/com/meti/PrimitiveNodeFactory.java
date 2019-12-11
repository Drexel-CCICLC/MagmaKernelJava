package com.meti;

import java.util.Optional;

public class PrimitiveNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		return Optional.of(new PrimitiveNode(PrimitiveStruct.INT, Integer.parseInt(value)));
	}

	private static class PrimitiveNode extends Node {
		public PrimitiveNode(PrimitiveStruct struct, int value) {
			super(struct, value);
		}

		@Override
		public String compile() {
			return this.value.toString();
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
