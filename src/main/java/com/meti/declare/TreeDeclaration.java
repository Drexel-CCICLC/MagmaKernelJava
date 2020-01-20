package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.declare.AssignNode;
import com.meti.node.bracket.declare.DeclareNode;
import com.meti.node.bracket.struct.ObjectType;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.other.AnyType;
import com.meti.node.value.compound.variable.FieldNodeBuilder;
import com.meti.node.value.compound.variable.VariableNode;
import com.meti.node.value.primitive.array.ArrayIndexNode;
import com.meti.node.value.primitive.array.ArraySizeNode;
import com.meti.node.value.primitive.integer.IntNode;
import com.meti.node.value.primitive.point.ReferenceNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.node.value.primitive.array.ArrayType.arrayOf;
import static com.meti.node.value.primitive.point.PointerType.pointerOf;

public class TreeDeclaration implements Declaration {
	private final List<Declaration> children = new ArrayList<>();
	private final boolean isParameter;
	private final String name;
	private final Declaration parent;
	private final Type type;

	TreeDeclaration(String name, Type type, boolean isParameter, Declaration parent) {
		this.name = name;
		this.type = type;
		this.isParameter = isParameter;
		this.parent = parent;
	}

	@Override
	public Collection<Node> buildAssignments(List<String> parameters) {
		return IntStream.range(0, parameters.size())
				.mapToObj(index -> buildSuperAssignment(index, parameters.get(index)))
				.collect(Collectors.toList());
	}

	private Node buildSuperAssignment(int index, String paramName) {
		Node paramNode = new VariableNode(paramName);
		Node pointerNode = new ReferenceNode(paramNode);
		Node arrayNode = new VariableNode(instanceName());
		Node indexNode = new IntNode(index);
		Node arrayIndexNode = new ArrayIndexNode(arrayNode, indexNode);
		return new AssignNode(arrayIndexNode, pointerNode);
	}

	private String instanceName() {
		return name + "_";
	}

	@Override
	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(get(name));
	}

	@Override
	public Node declareInstance(int paramSize) {
		Type pointerType = pointerOf(AnyType.INSTANCE);
		Type arrayType = arrayOf(pointerType);
		Node sizeNode = new IntNode(paramSize);
		Node arraySizeNode = new ArraySizeNode(pointerType, sizeNode);
		return new DeclareNode(arrayType, name, arraySizeNode);
	}

	@Override
	public void define(String name, Type type, boolean isParameter) {
		Declaration child = new TreeDeclaration(name, type, isParameter, this);
		children.add(child);
	}

	@Override
	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	@Override
	public boolean isParameter() {
		return isParameter;
	}

	@Override
	public FieldNodeBuilder lookupFieldOrder(String name, FieldNodeBuilder builder) {
		int order = orderOf(name).orElseThrow();
		return builder.withOrder(order);
	}

	@Override
	public FieldNodeBuilder lookupFieldType(FieldNodeBuilder builder, String childName) {
		Type type = child(childName).orElseThrow().type();
		return builder.withType(type);
	}

	@Override
	public boolean matches(String name) {
		return this.name.equals(name);
	}

	@Override
	public OptionalInt orderOf(String name) {
		return IntStream.range(0, children.size())
				.filter(value -> children.get(value).matches(name))
				.findFirst();
	}

	@Override
	public Optional<Declaration> parent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public Map<String, Type> toInstance(Declarations source) {
		Type type = new ObjectType(source, name);
		return Map.of(instanceName(), type);
	}

	@Override
	public Node toInstance() {
		return new VariableNode(name);
	}

	@Override
	public StructNodeBuilder toStruct(Map<String, Type> parameters, Type returnType, Node block) {
		return StructNodeBuilder.create()
				.withParameters(parameters)
				.withReturnType(returnType)
				.withBlock(block)
				.withName(name);
	}

	@Override
	public Node toSuperVariable() {
		return new VariableNode(instanceName());
	}

	@Override
	public Type type() {
		return type;
	}

	private Declaration get(String name) {
		return children.stream()
				.filter(child -> child.matches(name))
				.findFirst()
				.orElseThrow();
	}
}
