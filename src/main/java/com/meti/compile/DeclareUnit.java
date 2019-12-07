package com.meti.compile;

import com.meti.interpret.DeclareStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;
import com.meti.interpret.Type;

import java.util.Optional;

import static com.meti.interpret.StatementProperty.NAME;
import static com.meti.interpret.StatementProperty.VALUE;
import static java.util.Collections.singletonList;

class DeclareUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof DeclareStatement) {
			String nameString = statement.getProperty(NAME);
			String name = translator.aliaser().alias(nameString);
			String value = translator.translate(singletonList(statement.getProperty(VALUE)));
			return Optional.of("var " + name + "=" + value + ";");
		} else {
			return Optional.empty();
		}
	}
}
