package com.meti.node.bracket.struct;

import com.meti.declare.Declarations;
import com.meti.node.Type;
import com.meti.node.other.AnyType;
import com.meti.node.other.VoidType;
import com.meti.node.value.primitive.array.ArrayType;
import com.meti.node.value.primitive.point.PointerType;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class ObjectType implements Type {
    private final Declarations declarations;
    private final String name;

    public ObjectType(Declarations declarations, String name) {
        this.declarations = declarations;
		this.name = name;
    }

    @Override
    public OptionalInt childOrder(String childName) {
        declarations.relative(this.name);
        String[] childArray = children().keySet().toArray(String[]::new);
        int length = childArray.length;
        for (int i = 0; i < length; i++) {
            if (childArray[i].equals(childName)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    private Map<String, ? extends Type> children() {
        return declarations.relative(name).orElseThrow().childMap();
    }

    @Override
    public Optional<Type> childType(String childName) {
        return Optional.ofNullable(children().get(childName));
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
