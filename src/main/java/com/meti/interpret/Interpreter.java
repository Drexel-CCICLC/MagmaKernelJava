package com.meti.interpret;

import java.io.IOException;
import java.util.Optional;

public interface Interpreter {
	Optional<String> parse(String content);

	String run(String content) throws IOException, InterruptedException;
}
