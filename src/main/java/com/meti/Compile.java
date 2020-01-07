package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Compile {
	private static final Path OUT = Paths.get("out");
	private static final MagmaCompiler compiler = new MagmaCompiler();
	private static final Set<String> headers = new HashSet<>();
	private static final Logger logger = Logger.getAnonymousLogger();

	private static void ensure(Path path, boolean isDirectory) {
		if (!Files.exists(path)) {
			try {
				if (isDirectory) {
					Files.createDirectory(path);
				} else {
					Files.createFile(path);
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to make file.", e);
			}
		}
	}

	public static void main(String[] args) {
		Path root = Paths.get("");
		String result = compiler.compileAll(readFromBuild(root));
		String headerString = headers.stream()
				.map(s -> "#include \"" + s + "\"\n")
				.collect(Collectors.joining());
		ensure(OUT, true);
		Path compile = OUT.resolve("compile.c");
		ensure(compile, false);
		try {
			Files.writeString(compile, headerString + result);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write output.", e);
		}

		try {
			Path executable = OUT.resolve("a.exe");
			Files.deleteIfExists(executable);

			runCommand("C:\\MinGW\\bin\\gcc", "compile.c");
			runCommand(executable.toAbsolutePath().toString());
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to delete previous executable.", e);
		}
	}

	private static String readFromBuild(Path directory) {
		Path build = directory.resolve("build");
		ensure(build, false);
		try {
			List<String> lines = Files.readAllLines(build);
			StringBuilder content = new StringBuilder();
			for (String line : lines) {
				Path path = directory.resolve(line);
				if (line.endsWith(".h")) {
					headers.add(line);
				} else if (Files.isDirectory(path)) {
					content.append(readFromBuild(path));
				} else {
					String childContent = String.join("", Files.readAllLines(path));
					content.append(childContent);
				}
			}
			return content.toString();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to read build file.", e);
		}
		return "";
	}

	private static void runCommand(String... command) {
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.inheritIO();
			builder.directory(OUT.toFile());
			builder.start().waitFor(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | IOException e) {
			logger.log(Level.SEVERE, "Failed to compile.", e);
		}
	}
}
