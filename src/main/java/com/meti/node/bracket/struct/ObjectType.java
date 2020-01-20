package com.meti.node.bracket.struct;

import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Type;
import com.meti.node.other.AnyType;
import com.meti.node.other.VoidType;
import com.meti.node.value.primitive.array.ArrayType;
import com.meti.node.value.primitive.point.PointerType;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

public class ObjectType implements Type {
	private final Declarations declarations;
	private final String name;

	public ObjectType(Declarations declarations, String name) {
		this.declarations = declarations;
		this.name = name;
	}

	@Override
	public OptionalInt childOrder(String childName) {
		return declarations.relative(this.name).orElseThrow().childOrder(childName);
	}

	@Override
	public Optional<Type> childType(String childName) {
		return declarations.relative(childName)
				.flatMap((Function<Declaration, Optional<Declaration>>) declaration -> declaration.child(childName))
				.map(Declaration::type);
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		Type any = AnyType.INSTANCE();
		Type pointer = PointerType.pointerOf(any);
		Type array = ArrayType.arrayOf(pointer);
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
