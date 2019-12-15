package com.meti;

import java.util.Optional;

public class AssignNodeFactory implements NodeFactory {
	private final NodeTree tree;

	public AssignNodeFactory(NodeTree tree) {
		this.tree = tree;
	}

	@Override
	public Optional<Node> parse(String value, Parser parser) {
		BucketManager manager = new QueueBucketManager(
				Bucket.build().exclude('='),
				Bucket.build().include('=').restrict(1),
				Bucket.build()).pour(value);
		if (!manager.isValid()) return Optional.empty();
		String name = manager.next();
		String equals = manager.next();
		String content = manager.next();
		if (!equals.equals("=")) {
			return Optional.empty();
		}
		return Optional.of(new AssignMold(parser.parse(name), parser.parse(content)));
	}

	@Override
	public Optional<Struct> parse(String value) {
		return Optional.empty();
	}

	private class AssignMold extends AbstractInheritedNode {
		private final Node name;

		public AssignMold(Node name, Node value) {
			super(value);
			this.name = name;
		}

		@Override
		public String compile(Aliaser aliaser) {
			return name.compile(aliaser) + "=" + value.compile(aliaser) + ";";
		}

		@Override
		public Node transform() {
			return this;
		}
	}
}
