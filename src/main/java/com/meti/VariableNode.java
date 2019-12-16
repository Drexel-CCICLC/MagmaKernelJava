package com.meti;

import java.util.Arrays;

final class VariableNode extends AbstractNode implements NamedNode {
	private final String[] name;

	protected VariableNode(Struct struct, String... name) {
		super(struct);
		this.name = name;
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		if (name.length == 1) {
			return aliaser.alias(name);
		} else {
			var parent = Arrays.copyOf(this.name, name.length - 1);
			var varInstance = tree.locateDeclaration(parent)
					.orElseThrow()
					.struct()
					.parentNode()
					.orElseThrow();
			var f = ((InheritedNode) varInstance).value();
			var children = ((ParentNode) ((FunctionNode) f)
					.getContent())
					.children();
			String last = name[name.length - 1];
			int index = -1;
			for (int i = 0; i < children.size(); i++) {
				var child = children.get(i);
				if (child instanceof NamedNode && ((NamedNode) child).name().equals(last)) {
					index = i;
				}
			}
			if (index == -1) {
				throw new IllegalStateException(Arrays.toString(name) + " is not defined.");
			}
			return aliaser.alias(name[0]) + "[" + index + "]";
		}
	}

	@Override
	public Node transform() {
		return this;
	}

	@Override
	public String name() {
		return String.join(".", name);
	}
}
