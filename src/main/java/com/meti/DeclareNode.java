package com.meti;

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
		if (value != null) {
			String renderedValue = value.render();
            if (renderedValue.isBlank()) {
				return "";
			} else {
				return type.render() + " " + name + "=" +
						renderedValue + ";";
			}
		} else {
			return type.render() + " " + name + ";";
		}
	}
}
