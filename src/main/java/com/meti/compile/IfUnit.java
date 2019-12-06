package com.meti.compile;

import com.meti.interpret.IfStatement;
import com.meti.interpret.Statement;
import com.meti.interpret.StatementProperty;

import java.util.Optional;

public class IfUnit implements Unit {
	@Override
	public Optional<String> translate(Statement statement, Translator translator) {
		if (statement instanceof IfStatement) {
			Statement condition = statement.getProperty(StatementProperty.CONDITION);
			Statement ifBlock = statement.getProperty(StatementProperty.VALUE);
			Statement elseBlock = statement.getProperty(StatementProperty.ALTERNATE);
			String conditionString = translator.translate(condition);
			String ifString = translator.translate(ifBlock);
			String elseString = translator.translate(elseBlock);
			return Optional.of("if(" + conditionString + ")" + ifString + "else" + elseString);
		}
		return Optional.empty();
	}
}
