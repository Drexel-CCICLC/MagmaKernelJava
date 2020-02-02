package com.meti.node.struct;

import com.meti.node.Type;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class ObjectType implements Type {
	private final Declarations declarations;
	private final Collection<String> stack;

	public ObjectType(Declarations declarations, Collection<String> stack) {
		this.declarations = declarations;
		this.stack = stack;
	}

	@Override
	public OptionalInt childOrder(String name) {
		List<String> childArray = lazyDeclaration()
				.children()
				.stream()
				.map(Declaration::name)
				.collect(Collectors.toList());
		int length = childArray.size();
		for (int i = 0; i < length; i++) {
			if (childArray.get(i).equals(name)) {
				return OptionalInt.of(i);
			}
		}
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String name) {
		return lazyDeclaration().child(name)
				.map(Declaration::type);
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		return new StructType(lazyDeclaration().name()).render();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public String render(String name) {
		return render() + " " + name;
	}

	private Declaration lazyDeclaration() {
		return declarations.absolute(stack);
	}
}
