package com.meti.interpret;

import java.util.List;
import java.util.Optional;

interface Type {
    String value();

    List<Type> parameters();

    Optional<Type> returnType();
}
