package com.meti.interpret;

import java.util.List;
import java.util.Optional;

public interface Type {
    Optional<String> value();

    List<Type> parameters();

    Optional<Type> returnType();
}
