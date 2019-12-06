package com.meti.compile;

import com.meti.interpret.Statement;

import java.util.Collections;
import java.util.List;

public interface Translator {
	Aliaser aliaser();

	default String translate(Statement statement) {
		return translate(Collections.singletonList(statement));
	}

	String translate(List<Statement> statements);
}
