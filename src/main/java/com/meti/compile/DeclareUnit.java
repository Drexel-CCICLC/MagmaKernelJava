package com.meti.compile;

import com.meti.interpret.DeclareStatement;
import com.meti.interpret.Statement;

import java.util.Collections;
import java.util.Optional;

import static com.meti.interpret.StatementProperty.NAME;
import static com.meti.interpret.StatementProperty.VALUE;

class DeclareUnit implements Unit {
    @Override
    public Optional<String> translate(Statement statement, Translator translator) {
        if (statement instanceof DeclareStatement) {
            return Optional.of("var " + statement.getProperty(NAME) +
                    "=" + translator.translate(Collections.singletonList(statement.getProperty(VALUE))));
        } else {
            return Optional.empty();
        }
    }
}
