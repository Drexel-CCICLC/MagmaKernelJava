package com.meti;

import java.util.Optional;

interface Declaration {
    StringBuilder callback();

    Optional<Declaration> child(String name);

    void define(String name, Type type, StringBuilder callback);

    String name();

    Type type();
}
