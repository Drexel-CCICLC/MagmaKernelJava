package com.meti.compile;

import com.meti.interpret.BlockStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof BlockStatement) {
			List<Statement> value = statement.getProperty(StatementProperty.VALUE);
			String lines = value.stream()
					.map(translator::translate)
					.collect(Collectors.joining());
			return Optional.of("{" + lines + "}");
		}
		return Optional.empty();
	}
}
