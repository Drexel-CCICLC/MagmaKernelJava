package com.meti;

import java.util.List;

public abstract class AbstractParentNode extends AbstractNode implements ParentNode {
	protected final List<Node> children;

	public AbstractParentNode(Struct struct, List<Node> children) {
		super(struct);
		this.children = children;
	}

	@Override
	public List<Node> children() {
		return children;
	}
}
