package com.meti.interpret;

import java.util.List;
import java.util.Map;

import static com.meti.interpret.StatementProperty.*;

class DeclareStatement implements Statement {
    private final Map<StatementProperty, Object> properties;

    DeclareStatement(Type type, boolean mutable, String name, Statement value) {
        this(Map.of(
                TYPE, type,
                MUTABLE, mutable,
                NAME, name,
                VALUE, value
        ));
    }

    DeclareStatement(Map<StatementProperty, Object> properties) {
        this.properties = properties;
    }

    @Override
    public <T> T getProperty(StatementProperty property) {
        return (T) properties.get(property);
    }
}
