package com.meti.node.struct;

import com.meti.node.Type;
import com.meti.node.array.PointerArrayType;
import com.meti.node.primitive.AnyType;
import com.meti.node.point.PointerType;

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
		Type array = new PointerArrayType(pointer);
		return array.render();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public String render(String name) {
		return render() + " " + name;
	}
}
