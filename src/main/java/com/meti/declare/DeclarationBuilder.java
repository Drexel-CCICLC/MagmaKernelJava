package com.meti.declare;

import com.meti.util.Flag;
import com.meti.type.Type;

import java.util.List;

public interface DeclarationBuilder {
    DeclarationBuilder withFlags(List<Flag> flags);

    DeclarationBuilder withType(Type type);

    DeclarationBuilder withName(String name);

    TreeDeclaration build();
}
