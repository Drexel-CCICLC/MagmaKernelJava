package com.meti.interpret;

import java.util.Map;

public class IfStatement extends MapStatement {
	IfStatement(Statement condition, Statement ifBlock, Statement elseBlock) {
		this(Map.of(
				StatementProperty.CONDITION, condition,
				StatementProperty.VALUE, ifBlock,
				StatementProperty.ALTERNATE, elseBlock
		));
	}

	IfStatement(Map<StatementProperty, Object> properties) {
		super(properties);
	}
}
