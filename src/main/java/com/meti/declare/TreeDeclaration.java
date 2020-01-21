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
	private final List<Declaration> children;
	private final Declarations declarations;
	private final boolean parameter;
	private final List<String> stack;
	private final Type type;

	TreeDeclaration(Type type, boolean parameter, Declarations declarations,
	                List<String> stack, List<Declaration> children) {
		this.type = type;
		this.parameter = parameter;
		this.declarations = declarations;
		this.stack = stack;
		this.children = children;
	}

	@Override
	public List<Declaration> ancestors() {
		List<String> buffer = new ArrayList<>();
		return IntStream.range(0, stack.size() - 1)
				.mapToObj(stack::get)
				.peek(buffer::add)
				.map(s -> declarations.absolute(buffer))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<Node> buildAssignments(List<? extends Parameter> parameters) {
		return IntStream.range(0, parameters.size())
				.mapToObj(index -> buildAssignment(index, parameters.get(index)))
				.collect(Collectors.toList());
	}

	private Node buildAssignment(int index, Parameter paramName) {
		Node paramNode = paramName.toNode();
		Node pointerNode = new ReferenceNode(paramNode);
		Node arrayNode = new VariableNode(instanceName());
		Node indexNode = new IntNode(index);
		Node arrayIndexNode = new ArrayIndexNode(arrayNode, indexNode);
		return new AssignNode(arrayIndexNode, pointerNode);
	}

	private String instanceName() {
		return name() + "_";
	}

	private String name() {
		if (stack.isEmpty()) {
			throw new IllegalStateException("Cannot locate name, stack is empty.");
		}
		return stack.get(stack.size() - 1);
	}

	@Override
	public Optional<Declaration> child(String name) {
		return children.stream()
				.filter(child -> child.matches(name))
				.findFirst();
	}

	@Override
	public Node declareInstance(int paramSize) {
		Type pointerType = pointerOf(AnyType.INSTANCE);
		Type arrayType = arrayOf(pointerType);
		Node sizeNode = new IntNode(paramSize);
		Node arraySizeNode = new ArraySizeNode(pointerType, sizeNode);
		return new DeclareNode(arrayType, name(), arraySizeNode);
	}

	@Override
	public Declaration define(String name, Type type) {
		List<String> stackCopy = new ArrayList<>(stack);
		stackCopy.add(name);
		Declaration child = TreeDeclarationBuilder.create()
				.withDeclarations(declarations)
				.withStack(stackCopy)
				.withType(type)
				.build();
		children.add(child);
		return child;
	}

	@Override
	public Declaration define(Parameter parameter) {
		List<String> stackCopy = new ArrayList<>(stack);
		stackCopy.add(parameter.name());
		Declaration child = TreeDeclarationBuilder.create()
				.withDeclarations(declarations)
				.withType(parameter.type())
				.withParameter(true)
				.withStack(stackCopy)
				.build();
		children.add(child);
		return child;
	}

	@Override
	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	@Override
	public boolean isParameter() {
		return parameter;
	}

	@Override
	public FieldNodeBuilder lookupFieldOrder(String name, FieldNodeBuilder builder) {
		int order = IntStream.range(0, children.size())
				.filter(value -> children.get(value).matches(name))
				.findFirst()
				.orElseThrow();
		return builder.withOrder(order);
	}

	@Override
	public FieldNodeBuilder lookupFieldType(String childName, FieldNodeBuilder builder) {
		Type type = child(childName).orElseThrow().type();
		return builder.withType(type);
	}

	@Override
	public boolean matches(String name) {
		return name().equals(name);
	}

	@Override
	public Optional<Declaration> parent() {
		List<String> parentStack = stack.subList(0, stack.size() - 1);
		return Optional.ofNullable(declarations.absolute(parentStack));
	}

	@Override
	public Node toInstance() {
		return new VariableNode(instanceName());
	}

	@Override
	public Parameter toInstancePair() {
		Type type = new ObjectType(declarations, name());
		return Parameter.of(instanceName(), type);
	}

	@Override
	public Node toParameter() {
		return new VariableNode(name());
	}

	@Override
	public StructNodeBuilder toStruct(Set<? extends Parameter> parameters, Type returnType, Node block) {
		return StructNodeBuilder.create()
				.withParameters(parameters)
				.withReturnType(returnType)
				.withBlock(block)
				.withName(name());
	}

	@Override
	public Type type() {
		return type;
	}

	@Override
	public String toString() {
		return "TreeDeclaration{" +
				"stack=" + stack +
				'}';
	}
}
