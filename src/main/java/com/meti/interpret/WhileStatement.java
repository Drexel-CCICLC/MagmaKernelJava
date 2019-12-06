package com.meti.interpret;

import java.util.Map;

public class WhileStatement extends MapStatement {
	WhileStatement(Statement condition, Statement content) {
		this(Map.of(
				StatementProperty.CONDITION, condition,
				StatementProperty.VALUE, content
		));
	}

	WhileStatement(Map<StatementProperty, Object> properties) {
		super(properties);
	}
}
