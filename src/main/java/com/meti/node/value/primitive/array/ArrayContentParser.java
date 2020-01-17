package com.meti.node.value.primitive.array;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Type;
import com.meti.node.bracket.block.BlockNode;
import com.meti.node.bracket.declare.AssignNode;
import com.meti.node.bracket.declare.DeclareNode;
import com.meti.node.value.primitive.integer.IntNode;
import com.meti.node.value.compound.invoke.InvocationNode;
import com.meti.node.bracket.struct.Generator;
import com.meti.node.bracket.struct.ReturnNode;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.value.compound.variable.VariableNode;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO: resolve coupling
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
			toReturn.add(buildNode(compiler, trim.substring(5)));
		}
		return toReturn;
	}

	private Node buildNode(Compiler compiler, String data) {
		Type type = parseType(compiler, data);
		Map<String, Node> children = parseChildren(compiler, data);
		return buildFunction(type, children);
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
		return Arrays.stream(contentString.split(","))
				.map(compiler::parseSingle)
				.collect(Collectors.toMap(node -> generator.next(), Function.identity()));
	}

	private Node buildFunction(Type type, Map<String, ? extends Node> children) {
		String name = generator.next();
		functions.add(createBuilder(type, children.keySet(), name));
		Node varNode = new VariableNode(name);
		return new InvocationNode(varNode, children.values(), false);
	}

	private StructNodeBuilder createBuilder(Type type, Collection<String> keys, String name) {
		Node block = buildBlock(type, keys);
		return createBuilder(type, keys)
				.withName(name)
				.withBlock(block)
				.withReturnType(new ArrayType(type));
	}

	private Node buildBlock(Type arrayType, Collection<String> keys) {
		List<Node> content = new ArrayList<>();
		content.add(buildDeclaration(arrayType, keys.size()));
		content.addAll(buildAssignments(keys));
		content.add(buildReturn());
		return new BlockNode(content);
	}

	private StructNodeBuilder createBuilder(Type arrayType, Collection<String> keys) {
		return keys.stream().reduce(StructNodeBuilder.create(),
				(structNodeBuilder, s) -> structNodeBuilder.withParameter(s, arrayType),
				(structNodeBuilder, structNodeBuilder2) -> structNodeBuilder);
	}

	private Node buildDeclaration(Type type, int size) {
		Type arrayType = new ArrayType(type);
		Node sizeNode = new IntNode(size);
		Node arraySizeNode = new ArraySizeNode(type, sizeNode);
		return new DeclareNode(arrayType, ARRAY_NAME, arraySizeNode);
	}

	private Collection<Node> buildAssignments(Collection<String> keys) {
		Counter counter = Counter.create();
		return keys.stream()
				.map(key -> buildAssignment(key, counter.poll()))
				.collect(Collectors.toList());
	}

	private Node buildReturn() {
		return new ReturnNode(ARRAY_VARIABLE);
	}

	private Node buildAssignment(String key, int index) {
		Node indexNode = new ArrayIndexNode(ARRAY_VARIABLE, new IntNode(index));
		Node varNode = new VariableNode(key);
		return new AssignNode(indexNode, varNode);
	}
}