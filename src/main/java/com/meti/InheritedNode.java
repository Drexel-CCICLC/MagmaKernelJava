package com.meti;

public abstract class InheritedNode extends AbstractNode {
	protected final Node node;

	public InheritedNode(Node node) {
		super(node.struct());
		this.node = node;
	}
}
