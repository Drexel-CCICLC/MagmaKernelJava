package com.meti.node.declare;

import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.struct.ObjectType;
import com.meti.node.struct.StructType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeDeclarations implements Declarations {
	private final Declaration root = new ValueDeclaration("root", null);
	private final Stack<String> stack = new Stack<>();

	@Override
	public Type toCurrentClass(String name) {
		List<String> stack = new ArrayList<>(this.stack);
		stack.add(name);
		return new ObjectType(this, stack);
	}

	@Override
	public String buildStackName() {
		return String.join("_", stack);
	}

	@Override
	public List<Parameter> buildStackParameters() {
		return stack.subList(0, stack.size() - 1).stream()
				.map(s -> Parameter.create(new StructType(s), s + "_"))
				.collect(Collectors.toList());
	}

	@Override
	public String currentName() {
		return stack.peek();
	}

	@Override
	public Declaration define(Parameter parameter) {
		return current().define(parameter);
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}


	@Override
	public Declaration absolute(Collection<String> stack) {
		//TODO: change to reduction
		Declaration current = root;
		for (String s : stack) {
			current = current.child(s).orElseThrow(() -> new IllegalArgumentException("Failed to find declaration " +
					"for:" +
					" " + String.join(",", stack)));
		}
		return current;
	}

	@Override
	public void define(Type type, String name) {
		current().define(type, name);
	}

	@Override
	public Declaration defineParent(Type type, String name) {
		return parent().define(type, name);
	}

	@Override
	public Declaration parent() {
		return absolute(stack.subList(0, stack.size() - 1));
	}

	@Override
	public <T> T inStack(String name, Function<? super String, T> mapper) {
		stack.push(name);
		T t = mapper.apply(name);
		stack.pop();
		return t;
	}

	@Override
	public boolean isRoot(Declaration declaration) {
		return root.equals(declaration);
	}

	@Override
	public Optional<Declaration> parent(String name) {
		Deque<String> deque = new LinkedList<>(stack);
		while (!deque.isEmpty()) {
			Declaration declaration = absolute(deque);
			Optional<Declaration> child = declaration.child(name);
			if (child.isPresent()) {
				return Optional.of(declaration);
			} else {
				deque.pollLast();
			}
		}
		Optional<Declaration> child = root.child(name);
		return child.isPresent() ? Optional.of(root) : Optional.empty();
	}

	@Override
	public Optional<Declaration> relative(String name) {
		Deque<String> stringDeque = new LinkedList<>(stack);
		while (!stringDeque.isEmpty()) {
			Optional<Declaration> optional = absolute(stringDeque).child(name);
			if (optional.isPresent()) {
				return optional;
			} else {
				stringDeque.pollLast();
			}
		}
		return root.child(name);
	}
}
