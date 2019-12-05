package com.meti.compile;

import com.meti.interpret.*;

import java.util.Optional;

import static com.meti.interpret.StatementProperty.NAME;
import static com.meti.interpret.StatementProperty.VALUE;
import static java.util.Collections.singletonList;

class AssignUnit implements Unit {
    @Override
    public Optional<String> translate(Statement statement, Translator translator) {
        if (statement instanceof AssignStatement) {
            String nameString = statement.getProperty(NAME);
            String name = translator.aliaser().alias(nameString);
            String value = translator.translate(singletonList(statement.getProperty(VALUE)));
            return Optional.of(build(name, value));
        } else {
            return Optional.empty();
        }
    }

    private String build(String name, String value) {
        return name + "=" + value + ";";
    }
}
