package com.meti.unit;

import com.meti.exception.AlreadyExistsException;
import com.meti.exception.DoesNotExistException;

import java.util.*;

public class MapDeclarations implements Declarations {
	private final Map<String, Declaration> declarations;

	public MapDeclarations() {
		this(new LinkedHashMap<>());
	}

	public MapDeclarations(Map<String, Declaration> declarations) {
		this.declarations = declarations;
	}

	@Override
	public Set<String> childrenOf(String... parent) {
		return findParent(parent).orElseThrow().children.keySet();
	}

	@Override
	public void define(String... name) {
		define(Collections.emptyList(), name);
	}

	@Override
	public void define(List<String> flags, String... name) {
		if (name.length == 1) {
			if (declarations.containsKey(name[0])) {
				throw new AlreadyExistsException("\"" + joinName(name) + "\" has already been defined.");
			} else {
				declarations.put(name[0], new Declaration(flags));
			}
		} else {
			Declaration parent = findParent(name).orElseThrow();
			if (parent.children.containsKey(name[name.length - 1])) {
				throw new AlreadyExistsException("\"" + joinName(name) + "\" has already been defined.");
			} else {
				parent.children.put(name[name.length - 1], new Declaration(flags));
			}
		}
	}

	@Override
	public void delete(String... name) {
		if (name.length == 1 && declarations.containsKey(name[0])) {
			declarations.remove(name[0]);
		} else if (find(name).isPresent()) {
			findParent(name).orElseThrow().children.remove(name[name.length - 1]);
		} else {
			throw new DoesNotExistException("\"" + joinName(name) + "\" has not been defined.");
		}
	}

	@Override
	public boolean hasFlag(String flag, String... name) {
		return isDefined(name) && find(name).orElseThrow().flags.contains(flag);
	}

	@Override
	public boolean hasAnyFlag(String flag, String name) {
		Optional<Declaration> declaration = find(name, declarations);
		if(declaration.isEmpty()) return false;
		return declaration.get().flags.contains(flag);
	}

	@Override
	public boolean isDefined(String... name) {
		return find(name).isPresent();
	}

	@Override
	public boolean isInScope(String... name) {
		Stack<String> stack = new Stack<>();
		Arrays.stream(name).forEach(stack::push);
		while (!stack.isEmpty()) {
			if (isDefined(stack.toArray(String[]::new))) {
				return true;
			}
			stack.pop();
		}
		return false;
	}

	@Override
	public int order(String name) {
		return orderHelper(name, declarations);
	}

	private Optional<Declaration> find(String[] name) {
		return find(name, name.length);
	}

	private String joinName(String[] name) {
		return String.join(",", name);
	}

	private Optional<Declaration> findParent(String[] name) {
		return find(name, name.length - 1);
	}

	private Optional<Declaration> find(String[] name, int extent) {
		Declaration current = declarations.get(name[0]);
		if (current == null) return Optional.empty();
		for (int i = 1; i < extent; i++) {
			current = current.children.get(name[i]);
			if (current == null) return Optional.empty();
		}
		return Optional.of(current);
	}

	private Optional<Declaration> find(String name, Map<String, Declaration> current) {
		if (current.containsKey(name)) {
			return Optional.of(current.get(name));
		} else if (current.isEmpty()) {
			return Optional.empty();
		} else {
			for (Declaration declaration : current.values()) {
				Map<String, Declaration> children = declaration.children;
				Optional<Declaration> result = find(name, children);
				if (result.isPresent()) return result;
			}
			return Optional.empty();
		}
	}

	private int orderHelper(String name, Map<String, Declaration> current) {
		if (current.containsKey(name)) {
			return new ArrayList<>(current.keySet()).indexOf(name);
		} else if (current.isEmpty()) {
			return -1;
		} else {
			for (Declaration declaration : current.values()) {
				Map<String, Declaration> children = declaration.children;
				int result = orderHelper(name, children);
				if (result != -1) return result;
			}
			return -1;
		}
	}

	public class Declaration {
		private final Map<String, Declaration> children;
		private final List<String> flags;

		public Declaration(List<String> flags) {
			this(flags, new LinkedHashMap<>());
		}

		public Declaration(List<String> flags, Map<String, Declaration> children) {
			this.flags = flags;
			this.children = children;
		}
	}
}