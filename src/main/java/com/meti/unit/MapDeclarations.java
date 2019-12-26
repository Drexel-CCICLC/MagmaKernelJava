package com.meti.unit;

import com.meti.exception.AlreadyExistsException;
import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;

import java.util.*;
import java.util.function.Supplier;

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
		return findParent(parent).orElseThrow().children().keySet();
	}

	@Override
	public void define(Type type, String... name) {
		define(Collections.emptyList(), type, name);
	}

	@Override
	public void define(Type type, Stack<String> stack, String name) {

	}

	@Override
	public void define(List<String> flags, Type type, String... name) {
		Declaration declaration = new DeclarationImpl(flags, type);
		if (name.length == 1) {
			if (declarations.containsKey(name[0])) {
				throw new AlreadyExistsException("\"" + joinName(name) + "\" has already been defined.");
			} else {
				declarations.put(name[0], declaration);
			}
		} else {
			Declaration parent = findParent(name).orElseThrow();
			if (parent.children().containsKey(name[name.length - 1])) {
				throw new AlreadyExistsException("\"" + joinName(name) + "\" has already been defined.");
			} else {
				parent.children().put(name[name.length - 1], declaration);
			}
		}
	}

	@Override
	public void delete(Stack<String> stack, String name) {

	}

	@Override
	public void delete(String... name) {
		if (name.length == 1 && declarations.containsKey(name[0])) {
			declarations.remove(name[0]);
		} else if (find(name).isPresent()) {
			findParent(name).orElseThrow().children().remove(name[name.length - 1]);
		} else {
			throw new DoesNotExistException("\"" + joinName(name) + "\" has not been defined.");
		}
	}

	@Override
	public Declaration get(Collection<String> stack) {
		return null;
	}

	@Override
	public Declaration get(Collection<String> stack, String name) {
		String[] alloc = new String[stack.size() + 1];
		stack.toArray(alloc);
		alloc[alloc.length - 1] = name;
		return find(alloc)
				.orElseThrow((Supplier<DoesNotExistException>) () -> {
					throw new DoesNotExistException(Arrays.toString(alloc) + " is not defined.");
				});
	}

	@Override
	public boolean hasFlag(String flag, String... name) {
		return isDefined(name) && find(name).orElseThrow().getFlags().contains(flag);
	}

	@Override
	public boolean hasAnyFlag(String flag, String name) {
		Optional<Declaration> declaration = find(name, declarations);
		if (declaration.isEmpty()) return false;
		return declaration.get().getFlags().contains(flag);
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
			current = current.children().get(name[i]);
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
				Map<String, Declaration> children = declaration.children();
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
				Map<String, Declaration> children = declaration.children();
				int result = orderHelper(name, children);
				if (result != -1) return result;
			}
			return -1;
		}
	}

	public class DeclarationImpl implements Declaration {
		private final Map<String, Declaration> children;
		private final List<String> flags;
		private final Type type;

		public DeclarationImpl(List<String> flags, Type type) {
			this(flags, new LinkedHashMap<>(), type);
		}

		public DeclarationImpl(List<String> flags, Map<String, Declaration> children, Type type) {
			this.flags = flags;
			this.children = children;
			this.type = type;
		}

		@Override
		public Optional<Declaration> child(String name) {
			return Optional.empty();
		}

		@Override
		public Map<String, Declaration> children() {
			return children;
		}

		@Override
		public void define(String name, Type type, Collection<String> flags) {

		}

		@Override
		public void delete(String name) {

		}

		@Override
		public Collection<String> getFlags() {
			return flags;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public boolean hasFlag(String value) {
			return getFlags().contains(value);
		}

		@Override
		public boolean isMutable() {
			return hasFlag("val");
		}
	}
}