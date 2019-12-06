package com.meti.compile;

import com.meti.interpret.IfStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;
import com.meti.interpret.WhileStatement;

import java.util.Optional;

public class WhileUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof WhileStatement) {
			Statement condition = statement.getProperty(StatementProperty.CONDITION);
			Statement contentBlock = statement.getProperty(StatementProperty.VALUE);
			String conditionString = translator.translate(condition);
			String contentString = translator.translate(contentBlock);
			return Optional.of("while(" + conditionString + ")" + contentString);
		}
		return Optional.empty();
	}
}
