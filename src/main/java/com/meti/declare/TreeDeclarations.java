package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TreeDeclarations implements Declarations {
	private final Declaration root = new TreeDeclaration("root", null, false, null, 0);
	private final Stack<String> stack = new Stack<>();

	@Override
	public Declaration root() {
		return root;
	}

	@Override
	public Node define(String name, Type type, Supplier<? extends Node> action) {
		define(name, type, false);
		stack.push(name);
		Node result = action.get();
		stack.pop();
		return result;
	}

	@Override
	public void define(String name, Type type, boolean isParameter) {
		current().define(name, type, isParameter);
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}

	private Declaration absolute(Collection<String> values) {
        return values.stream().reduce(root,
				(declaration, s) -> declaration.child(s).orElseThrow(),
				(declaration, declaration2) -> declaration2);
	}

	@Override
	public Optional<Declaration> relative(String value) {
		Deque<String> reverseStack = new LinkedList<>(stack);
		while (!reverseStack.isEmpty()) {
			Optional<Declaration> optional = absolute(reverseStack).child(value);
			if (optional.isPresent()) return optional;
			reverseStack.pollLast();
		}
		return root.child(value);
	}

	@Override
	public Stream<Declaration> stream() {
		Collection<Declaration> declarations = new ArrayList<>();
		Deque<String> reverseStack = new LinkedList<>(stack);
		while (!reverseStack.isEmpty()) {
			declarations.add(absolute(reverseStack));
			reverseStack.pollLast();
		}
		declarations.add(root);
		return declarations.stream();
	}

	@Override
	public void define(String name, Type type, Runnable action) {
		define(name, type, false);
		stack.push(name);
		action.run();
		stack.pop();
	}
}
