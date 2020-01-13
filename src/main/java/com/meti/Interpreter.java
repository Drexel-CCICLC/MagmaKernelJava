package com.meti;

import java.io.IOException;

public interface Interpreter {
	String run(String content) throws IOException, InterruptedException;
}
