package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.declare.AssignNode;
import com.meti.node.bracket.declare.DeclareNode;
import com.meti.node.bracket.struct.ObjectType;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.other.AnyType;
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
	private final Map<String, Declaration> children = new LinkedHashMap<>();
	private final int index;
	private final boolean isParameter;
	private final String name;
	private final Declaration parent;
	private final Type type;

	TreeDeclaration(String name, Type type, boolean isParameter, Declaration parent, int index) {
		this.name = name;
		this.type = type;
		this.isParameter = isParameter;
		this.parent = parent;
		this.index = index;
	}

	@Override
	public Collection<Node> buildSuperConstructors(Node size, List<String> copy) {
		Collection<Node> nodes = IntStream.range(0, copy.size())
				.mapToObj(index -> buildSuperAssignment(index, copy.get(index)))
				.collect(Collectors.toList());
		nodes.add(size);
		return nodes;
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
		return Optional.ofNullable(children.get(name));
	}

	@Override
	public OptionalInt childOrder(String name) {
		return children.keySet()
				.stream()
				.filter(child -> child.equals(name))
				.map(children::get)
				.mapToInt(Declaration::index)
				.findFirst();
	}

	@Override
	public Type childType(String childType) {
		return children.get(childType).type();
	}

	@Override
	public StructNodeBuilder createStructBuilder() {
		return StructNodeBuilder.create()
				.withName(name);
	}

	@Override
	public Node declareInstance(Compiler compiler, int paramSize) {
		Type pointerType = pointerOf(AnyType.INSTANCE);
		Type arrayType = arrayOf(pointerType);
		Node sizeNode = new IntNode(paramSize);
		Node arraySizeNode = new ArraySizeNode(pointerType, sizeNode);
		return new DeclareNode(arrayType, name, arraySizeNode);
	}

	@Override
	public void define(String name, Type type, boolean isParameter) {
		int childrenSize = children.size();
		Declaration child = new TreeDeclaration(name, type, isParameter, this, childrenSize);
		children.put(name, child);
	}

	@Override
	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public boolean isParameter() {
		return isParameter;
	}

	@Override
	public boolean matches(String name) {
		return this.name.equals(name);
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
	public Node toSuperVariable() {
		return new VariableNode(instanceName());
	}

	@Override
	public Type type() {
		return type;
	}
}
