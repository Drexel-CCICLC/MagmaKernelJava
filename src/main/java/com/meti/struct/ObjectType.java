package com.meti.struct;

import com.meti.Type;
import com.meti.array.ArrayType;
import com.meti.other.AnyType;
import com.meti.point.PointerType;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class ObjectType implements Type {
	private final Map<String, Type> children;

	public ObjectType(Map<String, Type> children) {
		this.children = children;
	}

	@Override
	public OptionalInt childOrder(String name) {
		String[] childArray = children.keySet().toArray(String[]::new);
		int length = childArray.length;
		for (int i = 0; i < length; i++) {
			if (childArray[i].equals(name)) {
				return OptionalInt.of(i);
			}
		}
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String name) {
		return Optional.ofNullable(children.get(name))
				.map(PointerType::new);
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		Type any = new AnyType();
		Type pointer = new PointerType(any);
		Type array = new ArrayType(pointer);
		return array.render();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}
