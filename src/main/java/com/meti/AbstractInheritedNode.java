package com.meti;

public abstract class AbstractInheritedNode extends AbstractNode implements InheritedNode {
	protected final Node value;

	public AbstractInheritedNode(Node value) {
		super(value.struct());
		this.value = value;
	}

	@Override
	public Node value() {
		return value;
	}
}
