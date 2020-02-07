package com.meti.core.task;

import java.io.IOException;
import java.util.logging.Logger;

public class RunTask extends ProcessTask {
	public RunTask(Logger logger) {
		super(logger);
	}

	@Override
	public boolean canExecute(String line) {
		return "run".equals(line);
	}

	@Override
	protected Process createProcess() throws IOException {
		return new ProcessBuilder()
				.command("a")
				.start();
	}
}
