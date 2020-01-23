package com.meti.interpret;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface Executable {
	String runImpl(String... command) throws IOException, InterruptedException;

	class ExecutableImpl implements Executable {
		@Override
		public String runImpl(String... command) throws IOException, InterruptedException {
			ByteArrayOutputStream error = new ByteArrayOutputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = builder.start();
			try (InputStream inputStream = process.getInputStream()) {
				inputStream.transferTo(output);
			}
			try (InputStream errorStream = process.getErrorStream()) {
				errorStream.transferTo(error);
			}
			process.waitFor();
			String result = error.toString();
			if (!result.isBlank()) {
				throw new IllegalStateException(result);
			}
			return output.toString();
		}
	}
}
