package com.meti.interpret;

import java.util.Map;

import static com.meti.interpret.StatementProperty.*;

public class DeclareStatement extends MapStatement {
    DeclareStatement(Type type, boolean mutable, String name, Statement value) {
        this(Map.of(
                TYPE, type,
                MUTABLE, mutable,
                NAME, name,
                VALUE, value
        ));
    }

    DeclareStatement(Map<StatementProperty, Object> properties) {
        super(properties);
    }

}
