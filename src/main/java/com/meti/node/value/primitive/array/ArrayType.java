package com.meti.node.value.primitive.array;

import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.Optional;
import java.util.OptionalInt;

public final class ArrayType implements Type {
	private final Type elementType;

	private ArrayType(Type elementType) {
		this.elementType = elementType;
	}

	public static Type arrayOf(Type elementType) {
		return new ArrayType(elementType);
	}

	@Override
	public OptionalInt childOrder(String childName) {
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String childType) {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		return elementType.render() + "*";
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
