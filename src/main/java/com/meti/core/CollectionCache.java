package com.meti.core;

import com.meti.Cache;
import com.meti.node.Node;
import com.meti.node.declare.DeclareNode;
import com.meti.node.declare.VariableNode;
import com.meti.node.point.AnyType;
import com.meti.node.point.PointerType;
import com.meti.node.primitive.IntNode;
import com.meti.node.primitive.IntType;
import com.meti.node.primitive.NullNode;
import com.meti.node.struct.BlockNode;
import com.meti.node.struct.FunctionNode;
import com.meti.node.struct.ReturnNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class CollectionCache implements Cache {
	private static final String EXIT_NAME = "_exitCode";
	private static final Node EXIT_DECLARE = new DeclareNode(IntType.INSTANCE, EXIT_NAME, new IntNode(0));
	private static final Node THROWS_DECLARE = new DeclareNode(new PointerType(AnyType.INSTANCE), "_throw",
			new NullNode());
	private final Collection<Node> functions;
	private final Collection<Node> main;
	private final Collection<Node> structs;

	public CollectionCache() {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	public CollectionCache(Collection<Node> structs, Collection<Node> functions, Collection<Node> main) {
		this.functions = functions;
		this.structs = structs;
		this.main = main;
	}

	@Override
	public void add(Node node) {
		main.add(node);
	}

	@Override
	public Node addFunction(Node function) {
		functions.add(function);
		return new EmptyNode();
	}

	@Override
	public void addStruct(Node struct) {
		structs.add(struct);
	}

	@Override
	public void clear() {
		functions.clear();
		structs.clear();
		main.clear();
	}

	@Override
	public String render() {
		String renderedStructs = renderNodes(structs);
		String renderedFunctions = renderNodes(functions);
		String renderedMain = renderMain();
		return EXIT_DECLARE.render() + THROWS_DECLARE.render() + renderedStructs + renderedFunctions + renderedMain;
	}

	private String renderNodes(Collection<? extends Node> nodes) {
		return nodes.stream()
				.map(Node::render)
				.collect(Collectors.joining());
	}

	private String renderMain() {
		main.add(new ReturnNode(new VariableNode(EXIT_NAME)));
		Node node = new FunctionNode("main", IntType.INSTANCE, Collections.emptyList(), new BlockNode(main));
		return node.render();
	}
}
