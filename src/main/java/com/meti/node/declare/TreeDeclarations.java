package com.meti.node.declare;

import com.meti.exception.ParseException;
import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.struct.ObjectType;
import com.meti.node.struct.StructType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.*;

public class TreeDeclarations implements Declarations {
	private final Set<Flag> flags = EnumSet.noneOf(Flag.class);
	private final Declaration root = new ValueDeclaration(emptyList(), null, emptySet());
	private final Stack<String> stack = new Stack<>();

	@Override
	public Type toCurrentClass(String name) {
		List<String> clone = new ArrayList<>(stack);
		if (!clone.isEmpty() && !clone.get(clone.size() - 1).equals(name)) {
			clone.add(name);
		}
		return new ObjectType(this, clone);
	}

	@Override
	public Set<Flag> flags() {
		return flags;
	}

	@Override
	public String buildStackName() {
		return String.join("_", stack);
	}

	@Override
	public List<Parameter> buildStackParameters() {
		return stack.subList(0, stack.size() - 1).stream()
				.map(s -> Parameter.create(new StructType(s), singletonList(s + "_")))
				.collect(Collectors.toList());
	}

	@Override
	public String currentName() {
		return stack.peek();
	}

	@Override
	public void define(Parameter parameter) {
		current().define(parameter);
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}


	@Override
	public Declaration absolute(Collection<String> stack) {
		try {
			return stack.stream().reduce(root,
					this::extractChild,
					(declaration, declaration2) -> declaration2);
		} catch (ParseException e) {
			throw new ParseException("Failed to find declaration " +
					"for: " + String.join(",", stack), e);
		}
	}

	private Declaration extractChild(Declaration declaration, String s) {
		return declaration.child(s).orElseThrow(() -> new ParseException("No such child " + s + " in " + declaration.joinStack()));
	}

	@Override
	public void define(Type type, String name, Set<Flag> flags) {
		current().define(type, name, flags);
	}

	@Override
	public void defineParent(Type type, String name, Set<Flag> flags) {
		parent().define(type, name, flags);
	}

	@Override
	public boolean isInClass() {
		return flags.contains(Flag.CLASS);
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
	public Type toCurrentClass() {
		return toCurrentClass(stack.peek());
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
		stringDeque.pollLast();
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
