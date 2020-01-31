package com.meti.node.declare;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.*;
import java.util.function.Function;

public class TreeDeclarations implements Declarations {
	private final Declaration root = new ValueDeclaration("root", null);
	private final Stack<String> stack = new Stack<>();

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
		Declaration current = root;
		for (String s : stack) {
			current = current.child(s).orElseThrow();
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
	public Stack<String> getStack() {
		return stack;
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
}
