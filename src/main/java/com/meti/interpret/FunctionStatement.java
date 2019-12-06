package com.meti.interpret;

import java.util.Map;

import static com.meti.interpret.StatementProperty.*;

public class FunctionStatement extends MapStatement {
	FunctionStatement(Map<String, Type> parameters, Type returnType, Statement block) {
		this(Map.of(
				PARAMETERS, parameters,
				RETURN, returnType,
				VALUE, block
		));
	}

	FunctionStatement(Map<StatementProperty, Object> properties) {
		super(properties);
	}
}
