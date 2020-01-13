package com.meti;

import java.io.IOException;

public interface Interpreter {
	String runCleanly(String content) throws IOException, InterruptedException;
}
