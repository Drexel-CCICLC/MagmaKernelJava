package com.meti;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloWorldTest {
	@Test
	void test() throws IOException, InterruptedException {
		String content = """
				#include <stdio.h>
				int main() {
					printf("%s", "Hello World!");
				}
				            """;
		Path tempOut = Paths.get("temp.c");
		Files.writeString(tempOut, content);

		run("gcc", "temp.c");
		String result = run("a");

		assertEquals("Hello World!", result);
		Files.deleteIfExists(tempOut);
		Files.deleteIfExists(tempOut.resolveSibling("a.exe"));
	}

	private String run(String... command) throws IOException, InterruptedException {
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
