package com.meti.core.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildTask implements Task {
	private final Logger logger;

	public BuildTask(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean canExecute(String line) {
		return "build".equals(line);
	}

	@Override
	public void execute(String line) {
		try {
			executeExceptionally();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to build.", e);
		}
	}

	private void executeExceptionally() throws IOException {
		logger.log(Level.INFO, "Building.");
		Process process = createProcess();
		try (InputStream input = process.getInputStream()) {
			input.transferTo(System.out);
		}
		try (InputStream error = process.getErrorStream()) {
			error.transferTo(System.err);
		}
	}

	private Process createProcess() throws IOException {
		return new ProcessBuilder()
				.command("gcc", "out.c")
				.start();
	}
}
