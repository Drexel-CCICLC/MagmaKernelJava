package com.meti.node.declare;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.struct.StructNode;
import com.meti.node.struct.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDeclaration implements Declaration {
	private final List<Declaration> children = new ArrayList<>();
	private final Set<Flag> flags;
	private final List<String> stack;
	private final Type type;

	AbstractDeclaration(List<String> stack, Type type, Set<Flag> flags) {
		this.stack = stack;
		this.type = type;
		this.flags = flags;
	}

	@Override
	public Optional<Declaration> child(String name) {
		return children.stream()
				.filter(declaration -> declaration.matches(name))
				.findFirst();
	}

	@Override
	public List<Declaration> children() {
		return children;
	}

	@Override
	public Node declareInstance() {
		return new DeclareNode(new StructType(name()),
				instanceName(), new VariableNode("{" + joinArgs() + "}"));
	}

	private List<Parameter> childrenAsParams() {
		return children().stream()
				.filter(child -> !child.isFunctional())
				.map(Declaration::toParameter)
				.collect(Collectors.toList());
	}

	@Override
	public Declaration define(Type type, String name, Set<Flag> flags) {
		List<String> copy = new ArrayList<>(stack);
		copy.add(name);
		Declaration declaration = new ValueDeclaration(copy, type, flags);
		children.add(declaration);
		return declaration;
	}

	@Override
	public Declaration define(Parameter parameter) {
		Declaration declaration = parameter.toDeclaration();
		children.add(declaration);
		return declaration;
	}

	@Override
	public String instanceName() {
		return name() + "_";
	}

	@Override
	public boolean isClass() {
		return flags.contains(Flag.CLASS);
	}

	@Override
	public boolean isFunctional() {
		return type.isFunctional();
	}

	@Override
	public boolean isNative() {
		return flags.contains(Flag.NATIVE);
	}

	@Override
	public boolean isSuperStructure() {
		return children.stream().anyMatch(Declaration::isFunctional);
	}

	@Override
	public String joinArgs() {
		return childrenAsParams().stream()
				.map(Parameter::name)
				.collect(Collectors.joining(","));
	}

	@Override
	public String joinStack() {
		return String.join("_", stack);
	}

	@Override
	public boolean matches(String name) {
		return this.name().equals(name);
	}

	@Override
	public String name() {
		return stack.get(stack.size() - 1);
	}

	@Override
	public String tempName() {
		return stack + "_";
	}

	@Override
	public Parameter toParameter() {
		return Parameter.create(type, stack);
	}

	@Override
	public List<Node> toParentParameters() {
		return stack.subList(0, stack.size() - 1)
				.stream()
				.map(s -> s + "_")
				.map(VariableNode::new)
				.collect(Collectors.toList());
	}

	@Override
	public Node toStruct() {
		return new StructNode(name(), childrenAsParams());
	}

	@Override
	public Node toVariable() {
		return new VariableNode(name() + "_");
	}

	@Override
	public Type type() {
		return type;
	}

	@Override
	public boolean hasParameter(String childName) {
		return child(childName).orElseThrow().isParameter();
	}
}
