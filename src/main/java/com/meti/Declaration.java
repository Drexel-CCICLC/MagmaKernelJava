package com.meti;

import java.util.Optional;

interface Declaration {
    StringBuilder callback();

    Optional<Declaration> child(String name);

    Declaration define(DeclarationBuilder builder);

    Type typeOf();

    boolean isMain();

    boolean isNative();

    String render();

    boolean matches(String name);

    String render(Compiler compiler, String value);
}
