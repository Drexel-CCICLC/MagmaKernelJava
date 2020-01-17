package com.meti.interpret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class CInterpreter implements Interpreter {
	private final Path cache = Paths.get("cache.c");

	@Override
	public Optional<String> parse(String content) {
		return Optional.empty();
	}

	@Override
	public String run(String content) throws IOException, InterruptedException {
		String result = compile(content);
		cleanup();
		return result;
	}

	private String compile(CharSequence content) throws IOException, InterruptedException {
		Files.writeString(cache, content);
		Executable.run("gcc", "cache.c");
		return Executable.run("a");
	}

	private void cleanup() throws IOException {
		Files.deleteIfExists(cache);
		Files.deleteIfExists(cache.resolveSibling("a.exe"));
	}
}