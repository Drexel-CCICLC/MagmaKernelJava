package com.meti.node.value.primitive.integer;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.Optional;

public class IntType implements Type {
	public static final Type INSTANCE = new IntType();

	@Override
	public Optional<String> name() {
		return Optional.empty();
	}

	@Override
	public Optional<Node> toField(Node instance, String name) {
		return Optional.empty();
	}

	@Override
	public boolean doesReturnVoid() {
		return returnType().isPresent() && returnType().get() instanceof VoidType;
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		return "int";
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
    public Optional<Type> childType(String childName) {
		return Optional.empty();
    }
}
