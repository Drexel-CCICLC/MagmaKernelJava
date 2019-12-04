package com.meti.interpret;

import java.util.Map;

import static com.meti.interpret.StatementProperty.TYPE;
import static com.meti.interpret.StatementProperty.VALUE;

public class PrimitiveStatement extends MapStatement {
    PrimitiveStatement(PrimitiveType type, Object value) {
        this(Map.of(
                TYPE, type,
                VALUE, value
        ));
    }

    private PrimitiveStatement(Map<StatementProperty, Object> properties) {
        super(properties);
    }
}
