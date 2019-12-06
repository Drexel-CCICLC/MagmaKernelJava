package com.meti.interpret;

import java.util.List;
import java.util.Map;

public class BlockStatement extends MapStatement {
	public BlockStatement(List<Statement> statements) {
		super(Map.of(
				StatementProperty.VALUE, statements
		));
	}
}
