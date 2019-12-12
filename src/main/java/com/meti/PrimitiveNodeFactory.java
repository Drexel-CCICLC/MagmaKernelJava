package com.meti;

import java.util.Optional;

public class PrimitiveNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		return Optional.of(new PrimitiveNode(PrimitiveStruct.INT, Integer.parseInt(value)));
	}

	public enum PrimitiveStruct implements Struct {
		CHAR, INT
	}

	public static class PrimitiveNode extends AbstractNode {
		public PrimitiveNode(Struct struct, int value) {
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
