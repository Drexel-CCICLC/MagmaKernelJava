package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.bracket.struct.ObjectType;
import com.meti.node.bracket.struct.StructNodeBuilder;
import com.meti.node.value.compound.variable.VariableNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreeDeclaration implements Declaration {
	private final Map<String, Declaration> children = new LinkedHashMap<>();
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
	public Node buildSuperAssignment(Compiler compiler, int index, String paramName) {
		return compiler.parseSingle(name + "_[" + index + "]=&" + paramName);
	}

	@Override
	public Collection<Node> buildSuperConstructors(Compiler compiler, Map<String, Type> parameters,
	                                               Node size) {
		List<String> list = new ArrayList<>(parameters.keySet());
		Collection<Node> nodes = IntStream.range(0, list.size())
				.mapToObj(index -> buildSuperAssignment(compiler, index, list.get(index)))
				.collect(Collectors.toList());
		nodes.add(size);
		return nodes;
	}

	@Override
	public Map<String, Type> childMap() {
		Map<String, Type> toReturn = new LinkedHashMap<>();
		children.forEach((s, declaration) -> toReturn.put(s, declaration.type()));
		return toReturn;
	}

	@Override
	public OptionalInt childOrder(String name) {
		String[] childArray = childMap().keySet().toArray(String[]::new);
		return IntStream.range(0, childArray.length)
				.filter(i -> childArray[i].equals(name))
				.findFirst();
	}

	@Override
	public Type childType(String childType) {
		return childMap().get(childType);
	}

	@Override
	public StructNodeBuilder createStructBuilder() {
		return StructNodeBuilder.create()
				.withName(name);
	}

	@Override
	public Node declareInstance(Compiler compiler, Map<String, Type> parameters) {
		return compiler.parseSingle("val " + name + "_=Array<Any*>(" + parameters.size() + ")");
	}

	@Override
	public void define(String name, Type type, boolean isParameter) {
		children.put(name, new TreeDeclaration(name, type, isParameter, this));
	}

	@Override
	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	@Override
	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	@Override
	public boolean isParameter() {
		return isParameter;
	}

	@Override
	public Optional<Declaration> parent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public Map<String, Type> toInstance(Declarations source) {
		Map<String, Type> temp = new HashMap<>();
		temp.put(name + "_", new ObjectType(source, name));
		return temp;
	}

	@Override
	public Node toInstance() {
		return new VariableNode(name);
	}

	@Override
	public Node toSuperVariable() {
		String s = name + "_";
		return new VariableNode(s);
	}

	@Override
	public Type type() {
		return type;
	}
}
