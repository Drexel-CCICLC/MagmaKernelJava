package com.meti;

import java.util.Collection;

public class MagmaInterpreter implements Interpreter {
	private final Collection<String> headers;

	MagmaInterpreter(Collection<String> headers) {
		this.headers = headers;
	}

	@Override
	public String run(String content) {
		throw new UnsupportedOperationException();
	}
}
