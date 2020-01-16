package com.meti.struct;

import com.meti.Type;
import com.meti.array.ArrayType;
import com.meti.other.AnyType;
import com.meti.other.VoidType;
import com.meti.point.PointerType;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class ObjectType implements Type {
	private final Map<String, ? extends Type> children;

	public ObjectType(Map<String, ? extends Type> children) {
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
		return Optional.ofNullable(children.get(name));
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
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public boolean doesReturnVoid() {
		return returnType().isPresent() && returnType().get() instanceof VoidType;
	}
}
