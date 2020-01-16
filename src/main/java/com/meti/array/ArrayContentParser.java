package com.meti.array;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.Type;
import com.meti.block.BlockNode;
import com.meti.declare.AssignNode;
import com.meti.declare.DeclareNode;
import com.meti.integer.IntNode;
import com.meti.invoke.InvocationNode;
import com.meti.struct.Generator;
import com.meti.struct.StructNodeBuilder;
import com.meti.variable.VariableNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayContentParser implements Parser {
	private static final String ARRAY_NAME = "array";
	private static final Node ARRAY_VARIABLE = new VariableNode("array");
	private final Functions functions;
	private final Generator generator;

	public ArrayContentParser(Functions functions, Generator generator) {
		this.functions = functions;
		this.generator = generator;
	}

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		Collection<Node> toReturn = new ArrayList<>();
		String trim = value.trim();
		if (trim.startsWith("Array") && trim.contains("{")) {
			toReturn.add(buildNode(compiler, trim));
		}
		return toReturn;
	}

	private Node buildNode(Compiler compiler, String trim) {
		String data = trim.substring(5);
		Type type = parseType(compiler, data);
		Map<String, Node> children = parseChildren(compiler, data);
		String[] childNames = children.keySet().toArray(String[]::new);
		Node name = buildFunction(type, childNames);
		return buildInvocation(children, name);
	}

	private Type parseType(Compiler compiler, String data) {
		int typeStart = data.indexOf('<') + 1;
		int typeEnd = data.indexOf('>');
		String typeString = data.substring(typeStart, typeEnd);
		return compiler.resolveName(typeString);
	}

	private Map<String, Node> parseChildren(Compiler compiler, String data) {
		int contentStart = data.indexOf('{') + 1;
		int contentEnd = data.indexOf('}');
		String contentString = data.substring(contentStart, contentEnd);
		Map<String, Node> children = new LinkedHashMap<>();
		Arrays.stream(contentString.split(","))
				.map(compiler::parseSingle)
				.forEach(node -> children.put(generator.next(), node));
		return children;
	}

	private Node buildFunction(Type type, String[] keys) {
		String name = generator.next();
		Node block = buildBlock(type, keys);
		StructNodeBuilder builder = createBuilder(type, keys);
		registerFunction(builder, name, block);
		return new VariableNode(name);
	}

	private void registerFunction(StructNodeBuilder builder, String name, Node block) {
		functions.add(builder.withName(name)
				.withBlock(block));
	}

	private Node buildInvocation(Map<String, ? extends Node> children, Node name) {
		ArrayList<Node> wrappedList = new ArrayList<>(children.values());
		return new InvocationNode(name, wrappedList, false);
	}

	private Node buildBlock(Type arrayType, String[] keys) {
		List<Node> content = new ArrayList<>();
		content.add(buildDeclaration(arrayType, keys.length));
		content.addAll(buildAssignments(keys));
		return new BlockNode(content);
	}

	private StructNodeBuilder createBuilder(Type arrayType, String[] keys) {
		StructNodeBuilder builder = StructNodeBuilder.create();
		Arrays.stream(keys).forEach(key -> builder.withParameter(key, arrayType));
		return builder;
	}

	private Node buildDeclaration(Type type, int size) {
		Type arrayType = new ArrayType(type);
		Node sizeNode = new IntNode(size);
		Node arraySizeNode = new ArraySizeNode(type, sizeNode);
		return new DeclareNode(arrayType, ARRAY_NAME, arraySizeNode);
	}

	private Collection<Node> buildAssignments(String[] keys) {
		return IntStream.range(0, keys.length)
				.mapToObj(i -> buildAssignment(keys[i], i))
				.collect(Collectors.toList());
	}

	private Node buildAssignment(String key, int index) {
		Node indexNode = new ArrayIndexNode(ARRAY_VARIABLE, new IntNode(index));
		Node varNode = new VariableNode(key);
		return new AssignNode(indexNode, varNode);
	}
}
