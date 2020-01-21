package com.meti.node.other;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.value.compound.variable.FieldNodeBuilder;

import java.util.Optional;
import java.util.OptionalInt;

public class AnyType implements Type {
    public static final Type INSTANCE = new AnyType();

    @Override
    public Optional<Node> toField(Node instance, String name) {
        Optional<Type> child = childType(name.trim());
        OptionalInt order = childOrder(name.trim());
        Node field =
                FieldNodeBuilder.create().withInstanceArray(instance).withOrder(order.orElseThrow()).withType(child.orElseThrow()).withName(name).build();
        return Optional.of(field);
	}

    @Override
    public Optional<Type> childType(String childName) {
        return Optional.empty();
    }

    private OptionalInt childOrder(String childName) {
        return OptionalInt.empty();
    }

    @Override
    public boolean doesReturnVoid() {
        return returnType().isPresent() && returnType().get() instanceof VoidType;
    }

    @Override
    public boolean isNamed() {
        throw new UnsupportedOperationException("AnyType cannot be named.");
    }

    @Override
    public String render() {
        return "...";
    }

    @Override
    public String renderWithName(String name) {
        return (isNamed()) ? render() : render() + " " + name;
    }

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
