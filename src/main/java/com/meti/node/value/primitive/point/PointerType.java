package com.meti.node.value.primitive.point;

import com.meti.node.Type;
import com.meti.node.other.AnyType;
import com.meti.node.other.VoidType;

import java.util.Optional;
import java.util.OptionalInt;

public class PointerType implements Type {
	private final Type child;

	private PointerType(Type child) {
		this.child = child;
	}

	public static Type pointerOf(Type child) {
		return new PointerType(child);
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
		return child.isNamed();
	}

	@Override
	public String render() {
		//TODO: rule changes with functions?
		return child instanceof AnyType ? "void*" : child.render() + "*";
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
