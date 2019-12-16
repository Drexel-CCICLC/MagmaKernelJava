package com.meti;

public abstract class AbstractInheritedNode extends AbstractNode implements InheritedNode {
	protected Node value;

	public AbstractInheritedNode(Struct struct, Node value) {
		super(struct);
		this.value = value;
	}

	public AbstractInheritedNode() {
		super(null);
	}

	public AbstractInheritedNode(Node value) {
		super(value.struct());
		this.value = value;
	}

	public void setValue(Node value) {
		this.value = value;
		this.struct = value.struct();
		value.setParent(this);
	}

	@Override
	public Node value() {
		if (value == null) throw new IllegalStateException("No value present.");
		return value;
	}
}
