package com.meti;

import java.util.List;

interface DeclarationBuilder {
    DeclarationBuilder withFlags(List<Flag> flags);

    DeclarationBuilder withType(Type type);

    DeclarationBuilder withName(String name);

    TreeDeclaration build();
}
