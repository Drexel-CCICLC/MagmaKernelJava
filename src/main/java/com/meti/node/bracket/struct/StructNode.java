package com.meti.node.bracket.struct;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class StructNode implements Node {
	private final Node block;
	private final String name;
	private final Map<String, ? extends Type> parameters;
	private final Type returnType;

	public StructNode(String name, Map<String, ? extends Type> parameters, Type returnType, Node block) {
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
		String paramString = joinParams();
		return returnType.render() + " " + name + "(" + paramString + ")" + block.render();
	}

	private String joinParams() {
		return parameters.keySet()
				.stream()
                .map(s -> parameters.get(s).render() + " " + s)
				.collect(Collectors.joining(","));
	}
}
