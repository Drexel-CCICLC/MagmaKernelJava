package com.meti.compile;

import com.meti.interpret.PrimitiveStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;

import java.util.Optional;

class IntUnit implements Unit {
    @Override
    public Optional<String> translate(Statement statement, Translator translator) {
        if (!(statement instanceof PrimitiveStatement)) {
            return Optional.empty();
        }
        int value = statement.getProperty(StatementProperty.VALUE);
        return Optional.of(String.valueOf(value));
    }
}
