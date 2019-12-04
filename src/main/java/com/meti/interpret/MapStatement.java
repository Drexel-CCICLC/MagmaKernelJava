package com.meti.interpret;

import java.util.Map;

class MapStatement implements Statement {
    protected final Map<StatementProperty, Object> properties;

    MapStatement(Map<StatementProperty, Object> properties) {
        this.properties = properties;
    }

    @Override
    public <T> T getProperty(StatementProperty property) {
        return (T) properties.get(property);
    }
}
