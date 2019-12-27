package com.meti;

import com.meti.exception.AlreadyExistsException;
import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

import static java.util.Collections.emptySet;

public class TreeDeclarations implements Declarations {
	private final Declaration root;
	private final Stack<String> stack = new Stack<>();
	private final Map<String, Declaration> temps = new HashMap<>();

	public TreeDeclarations() {
		this(new MutableTreeDeclaration(emptySet(), null, "root"));
	}

	public TreeDeclarations(Declaration root) {
		this.root = root;
	}

	@Override
	public void defineTemp(String tempName, Collection<String> tempFlags) {
		Declaration declaration = new MutableTreeDeclaration(tempFlags, tempName);
		temps.put(tempName, declaration);
	}

	@Override
	public Declaration define(String name, Type type, Collection<String> flags) {
		Declaration declaration = temps.remove(name);
		Declaration current = current();
		if (declaration instanceof MutableDeclaration) {
			((MutableDeclaration) declaration).setType(type);
			if (current.child(name).isPresent()) {
				throw new AlreadyExistsException(name + " is already defined.");
			} else {
				current.children().put(name, declaration);
			}
			return declaration;
		} else {
			return current.define(name, type, flags);
		}
	}

	@Override
	public Declaration current() {
		return absolute(stack);
	}

	@Override
	public Declaration absolute(Collection<String> splitName) {
		Declaration current = root;
		Collection<String> innerStack = new ArrayList<>();
		if (!splitName.isEmpty()) {
			String name = (String) splitName.toArray()[0];
			if (temps.containsKey(name)) {
				return temps.get(name);
			}
		}
		for (String name : splitName) {
			Optional<Declaration> optional = current.child(name);
			if (optional.isPresent()) {
				innerStack.add(name);
				current = optional.get();
			} else {
				innerStack.add(name);
				String joinedNames = String.join(".", innerStack);
				throw new DoesNotExistException(joinedNames + " is not defined.");
			}
		}
		return current;
	}

	@Override
	public Declaration define(String name, Type type) {
		return define(name, type, emptySet());
	}

	@Override
	public void delete(String name) {
		current().delete(name);
	}

	@Override
	public Declaration relative(String name) {
		return relativeOptionally(name)
				.orElseThrow(() -> new DoesNotExistException(name + " is not defined in scope."));
	}

	@Override
	public Optional<Declaration> relativeOptionally(String name) {
		Queue<String> copy = new PriorityQueue<>(stack);
		while (true) {
			Declaration frame = absolute(copy);
			Optional<Declaration> child = frame.child(name);
			if (child.isPresent()) {
				return child;
			} else {
				if (copy.isEmpty()) {
					break;
				}
				copy.poll();
			}
		}
		return Optional.empty();
	}

	@Override
	public void sink(String name) {
		stack.push(name);
	}

	@Override
	public void surface() {
		stack.pop();
	}


}
