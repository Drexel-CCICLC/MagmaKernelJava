package com.meti.core;

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
	public boolean execute(String line) {
		if (line.equals("build")) {
			logger.log(Level.INFO, "Building.");
			try {
				Process process = new ProcessBuilder()
						.command("gcc", "out.c")
						.start();
				try (InputStream input = process.getInputStream()) {
					input.transferTo(System.out);
				}
				try (InputStream error = process.getErrorStream()) {
					error.transferTo(System.err);
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to build.", e);
			}
			return true;
		} else {
			return false;
		}
	}
}
