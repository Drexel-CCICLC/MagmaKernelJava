package com.meti.core.task;

import java.io.IOException;
import java.util.logging.Logger;

public class BuildTask extends ProcessTask {
	public BuildTask(Logger logger) {
		super(logger);
	}

	@Override
	public boolean canExecute(String line) {
		return "build".equals(line);
	}

	@Override
	protected Process createProcess() throws IOException {
		return new ProcessBuilder()
				.command("gcc", "out.c")
				.start();
	}
}
