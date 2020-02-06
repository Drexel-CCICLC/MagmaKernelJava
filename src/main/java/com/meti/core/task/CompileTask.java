package com.meti.core.task;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.exception.CompileException;
import com.meti.util.CollectionCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CompileTask implements Task {
	private static final Path BUILD = Paths.get("build");
	private static final Path ROOT = Paths.get("");
	private final Cache cache = new CollectionCache();
	private final Compiler compiler = new MagmaCompiler(cache);
	private final Collection<String> headers = new HashSet<>();
	private final Logger logger;

	public CompileTask(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean execute(String line) {
		if (line.startsWith("compile")) {
			logger.log(Level.INFO, "Compiling sources.");
			cache.clear();
			headers.clear();
			headers.add("stddef.h");
			logger.log(Level.INFO, "Located " + headers.size() + " headers.");
			run();
			return true;
		} else {
			return false;
		}
	}

	private void parseCache() {
		try {
			String content = readBuild(ROOT);
			partition(content).stream()
					.filter(s -> !s.isBlank())
					.map(compiler::parse)
					.forEach(cache::add);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to read build file.", e);
		}
	}

	private List<String> partition(String content) {
		List<String> partitions = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();
		int depth = 0;
		for (char c : content.toCharArray()) {
			if (';' == c && 0 == depth) {
				partitions.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				if ('{' == c) depth++;
				if ('}' == c) depth--;
				buffer.append(c);
			}
		}
		partitions.add(buffer.toString());
		return partitions;
	}

	private String readBuild(Path directory) throws IOException {
		Path build = directory.resolve(BUILD);
		if (!Files.exists(build)) {
			Files.createFile(build);
		}
		List<String> lines = Files.readAllLines(build);
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			String trim = line.trim();
			if (!trim.isBlank()) {
				if (trim.endsWith(".h")) {
					readHeader(trim);
				} else if (trim.endsWith(".magma")) {
					builder.append(readPath(trim, directory));
				} else if (Files.isDirectory(directory.resolve(trim))) {
					builder.append(readDirectory(trim, directory));
				} else {
					throw new CompileException("Unknown child: " + trim);
				}
			}
		}
		return builder.toString();
	}

	private String readDirectory(String trim, Path directory) throws IOException {
		return readBuild(directory.resolve(trim));
	}

	private void readHeader(String value) {
		headers.add(value.trim());
	}

	private String readPath(String value, Path directory) throws IOException {
		String trim = value.trim();
		Path path = directory.resolve(trim);
		List<String> lines = Files.readAllLines(path);
		return String.join("", lines);
	}

	private void run() {
		parseCache();
		writeOutput();
	}

	private void writeOutput() {
		try {
			Path out = ROOT.resolve("out.c");
			String headerString = headers.stream()
					.map(s -> "#include <" + s + ">\n")
					.collect(Collectors.joining());
			String output = headerString + cache.render();
			Files.writeString(out, output);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write output.", e);
		}
	}
}
