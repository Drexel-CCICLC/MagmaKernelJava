package com.meti;

import java.io.IOException;
import java.util.Optional;

interface Interpreter {
	Optional<String> parse(String content);

	String run(String content) throws IOException, InterruptedException;
}
