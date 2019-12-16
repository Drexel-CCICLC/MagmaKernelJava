package com.meti;

final class ReturnNode extends AbstractInheritedNode {
	public ReturnNode(Node value) {
		super(value);
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		return "return " + value.compile(aliaser, tree) + ";";
	}

	@Override
	public Node transform() {
		return this;
	}
}
