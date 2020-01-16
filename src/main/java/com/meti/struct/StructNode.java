package com.meti.struct;

import com.meti.Node;
import com.meti.Type;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class StructNode implements Node {
	private final Node block;
	private final String name;
	private final Map<String, Type> parameters;
	private final Type returnType;

	public StructNode(String name, Map<String, Type> parameters, Type returnType, Node block) {
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.block = block;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		String paramString = parameters.keySet()
				.stream()
                .map(s -> parameters.get(s).render() + " " + s)
				.collect(Collectors.joining(","));
		return returnType.render() + " " + name + "(" + paramString + ")" + block.render();
	}
}
