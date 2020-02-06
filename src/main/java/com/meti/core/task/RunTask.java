package com.meti.core.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunTask implements Task {
	private final Logger logger;

	public RunTask(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean canExecute(String line) {
		return line.equals("run");
	}

	@Override
	public void execute(String line) {
		logger.log(Level.INFO, "Running.");
		try {
			Process process = new ProcessBuilder()
					.command("a")
					.start();
			try (InputStream input = process.getInputStream()) {
				input.transferTo(System.out);
			}
			try (InputStream error = process.getErrorStream()) {
				error.transferTo(System.err);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to run.", e);
		}
	}
}
