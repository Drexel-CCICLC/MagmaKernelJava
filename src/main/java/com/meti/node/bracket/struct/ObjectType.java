package com.meti.node.bracket.struct;

import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.other.AnyType;
import com.meti.node.other.VoidType;
import com.meti.node.value.compound.variable.FieldNodeBuilder;
import com.meti.node.value.primitive.array.ArrayType;
import com.meti.node.value.primitive.point.PointerType;

import java.util.Optional;

public class ObjectType implements Type {
    private final Declarations declarations;
    private final String name;

    public ObjectType(Declarations declarations, String name) {
        this.declarations = declarations;
        this.name = name;
    }

    @Override
    public Optional<Node> toField(Node instance, String name) {
        FieldNodeBuilder builder = FieldNodeBuilder.create()
                .withName(name)
                .withInstanceArray(instance);
        builder = lookupFieldType(builder, name);
        builder = lookupFieldOrder(builder, name);
        return Optional.ofNullable(builder.build());
    }

    private FieldNodeBuilder lookupFieldType(FieldNodeBuilder builder, String name) {
        return declaration().lookupFieldType(name, builder);
    }

    private FieldNodeBuilder lookupFieldOrder(FieldNodeBuilder builder, String name) {
        return declaration().lookupFieldOrder(name, builder);
    }

    private Declaration declaration() {
        return declarations.relative(this.name).orElseThrow();
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
        Type pointer = PointerType.pointerOf(AnyType.INSTANCE);
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
    public Optional<Type> childType(String childName) {
        return declaration()
                .child(childName)
                .map(Declaration::type);
    }
}
