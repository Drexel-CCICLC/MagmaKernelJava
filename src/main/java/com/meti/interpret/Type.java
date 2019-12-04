package com.meti.interpret;

import java.util.List;
import java.util.Optional;

public interface Type {
    String value();

    List<Type> parameters();

    Optional<Type> returnType();
}
