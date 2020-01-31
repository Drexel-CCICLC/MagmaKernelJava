package com.meti.declare;

import com.meti.Node;
import com.meti.Type;

public class DeclareNode implements Node {
	private final String name;
	private final Type type;
	private final Node value;

	public DeclareNode(Type type, String name, Node value) {
		this.value = value;
		this.type = type;
		this.name = name;
	}

	@Override
	public String render() {
		if (null != value) {
			String renderedValue = value.render();
			return renderedValue.isBlank() ? "" :
					type.render(name) + "=" +
							renderedValue + ";";
		} else {
			return type.render(name) + ";";
		}
	}
}
