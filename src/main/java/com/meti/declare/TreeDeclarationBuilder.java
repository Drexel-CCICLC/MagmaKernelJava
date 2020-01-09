package com.meti.declare;

import com.meti.util.Flag;
import com.meti.type.Type;
import com.meti.type.AnyType;

import java.util.Collections;
import java.util.List;

public class TreeDeclarationBuilder implements DeclarationBuilder {
    private final List<Flag> flags;
    private final String name;
    private final Type type;

    private TreeDeclarationBuilder() {
        this(Collections.emptyList(), AnyType.INSTANCE, null);
    }

    private TreeDeclarationBuilder(List<Flag> flags, Type type, String name) {
        this.flags = flags;
        this.type = type;
        this.name = name;
    }

    public static DeclarationBuilder create() {
        return new TreeDeclarationBuilder();
    }

    @Override
    public DeclarationBuilder withFlags(List<Flag> flags) {
        return new TreeDeclarationBuilder(flags, type, name);
    }

    @Override
    public DeclarationBuilder withType(Type type) {
        return new TreeDeclarationBuilder(flags, type, name);
    }

    @Override
    public DeclarationBuilder withName(String name) {
        return new TreeDeclarationBuilder(flags, type, name);
    }

    @Override
    public TreeDeclaration build() {
        if (name == null) throw new IllegalStateException("Declaration must have a name.");
        return new TreeDeclaration(flags, type, name);
    }
}