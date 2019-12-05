package com.meti.interpret;

import java.util.Map;

public class VariableStatement extends MapStatement {
	VariableStatement(Type type, String name) {
		this(Map.of(
				StatementProperty.TYPE, type,
				StatementProperty.NAME, name
		));
	}

	VariableStatement(Map<StatementProperty, Object> properties) {
		super(properties);
	}
}
