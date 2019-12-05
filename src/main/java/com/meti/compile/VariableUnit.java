package com.meti.compile;

import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;
import com.meti.interpret.VariableStatement;

import java.util.Optional;

public class VariableUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof VariableStatement) {
			String name = statement.getProperty(StatementProperty.NAME);
			return Optional.of(translator.aliaser().alias(name));
		} else {
			return Optional.empty();
		}
	}
}
