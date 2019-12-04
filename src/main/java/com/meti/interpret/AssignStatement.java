package com.meti.interpret;

import java.util.Map;

import static com.meti.interpret.StatementProperty.*;

public class AssignStatement extends MapStatement {
    AssignStatement(Type type, String name, Statement value) {
        this(Map.of(
                TYPE, type,
                NAME, name,
                VALUE, value
        ));
    }

    private AssignStatement(Map<StatementProperty, Object> properties) {
        super(properties);
    }

}
