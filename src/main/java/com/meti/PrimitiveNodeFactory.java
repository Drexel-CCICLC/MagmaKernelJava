package com.meti;

import java.util.Optional;

public class PrimitiveNodeFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value, Parser parser) {
		var trimmedValue = value.trim();
		if (trimmedValue.equals("true") || trimmedValue.equals("false")) {
			return Optional.of(new PrimitiveNode(PrimitiveStruct.BOOL, Boolean.parseBoolean(trimmedValue)));
		}

		try {
			return Optional.of(new PrimitiveNode(PrimitiveStruct.INT, Integer.parseInt(trimmedValue)));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Struct> parse(String value) {
		if (value.equals("int")) {
			return Optional.of(PrimitiveStruct.INT);
		}
		return Optional.empty();
	}

	public enum PrimitiveStruct implements Struct {
		BOOL,
		CHAR,
		INT,
		ANY;

		@Override
		public boolean isInstance(Struct other) {
			return this == other;
		}

		@Override
		public Struct merge(Struct root) {
			if (isInstance(root)) {
				return root;
			}
			return root.parent()
					.map(this::merge)
					.orElse(ANY);
		}

		@Override
		public Optional<Struct> parent() {
			return Optional.empty();
		}
	}

	public static class PrimitiveNode extends AbstractNode {
		private final Object value;

		public PrimitiveNode(Struct struct, Object value) {
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
