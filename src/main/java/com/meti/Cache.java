package com.meti;

import com.meti.primitive.IntNode;
import com.meti.primitive.IntType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class Cache {
	private static final String EXIT_NAME = "_exit";
	private static final Node EXIT_DECLARE = new DeclareNode(IntType.INSTANCE, EXIT_NAME, new IntNode(0));
	private final Collection<Node> functions;
	private final Collection<Node> main;
	private final Collection<Node> structs;

	public Cache() {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	public Cache(Collection<Node> structs, Collection<Node> functions, Collection<Node> main) {
		this.functions = functions;
		this.structs = structs;
		this.main = main;
	}

	public void add(Node node) {
		main.add(node);
	}

	public void addFunction(Node function) {
		functions.add(function);
	}

	public void addStruct(Node struct) {
		structs.add(struct);
	}

	public String render() {
		String renderedStructs = renderNodes(structs);
		String renderedFunctions = renderNodes(functions);
		String renderedMain = renderMain();
		return EXIT_DECLARE.render() + renderedStructs + renderedFunctions + renderedMain;
	}

	private String renderNodes(Collection<? extends Node> nodes) {
		return nodes.stream()
				.map(Node::render)
				.collect(Collectors.joining());
	}

	private String renderMain() {
		main.add(new ReturnNode(new VariableNode("_exit")));
		Node node = new FunctionNode("main", IntType.INSTANCE, Collections.emptyList(), new BlockNode(main));
		return node.render();
	}
}
