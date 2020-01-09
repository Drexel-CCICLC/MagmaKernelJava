package com.meti.declare;

import com.meti.compile.Compiler;
import com.meti.type.Type;

import java.util.Optional;

public interface Declaration {
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
