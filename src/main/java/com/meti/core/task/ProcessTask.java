package com.meti.core.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ProcessTask implements Task {
	protected final Logger logger;

	public ProcessTask(Logger logger) {
		this.logger = logger;
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
		transferProcessStream(process.getErrorStream(), System.err);
		transferProcessStream(process.getInputStream(), System.out);
	}

	protected abstract Process createProcess() throws IOException;

	private void transferProcessStream(InputStream errorStream, PrintStream err) throws IOException {
		try (InputStream error = errorStream) {
			error.transferTo(err);
		}
	}
}
