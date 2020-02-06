package com.meti.core.task;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.exception.CompileException;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.BracketPartitioner;
import com.meti.util.CollectionCache;
import com.meti.util.Partitioner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private final Declarations declarations = new TreeDeclarations();
	private final Compiler compiler = new MagmaCompiler(cache, declarations);
	private final Collection<String> headers = new HashSet<>();
	private final Logger logger;

	public CompileTask(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean canExecute(String line) {
		return line.startsWith("compile");
	}

	private Path ensureBuild(Path directory) throws IOException {
		Path build = directory.resolve(BUILD);
		if (!Files.exists(build)) {
			Files.createFile(build);
		}
		return build;
	}

	private void clearFields() {
		cache.clear();
		headers.clear();
		declarations.clear();
	}

	@Override
	public void execute(String line) {
		logger.log(Level.INFO, "Compiling sources.");
		clearFields();
		resetHeaders();
		processCache();
	}

	private void resetHeaders() {
		headers.add("stddef.h");
		logger.log(Level.INFO, "Located " + headers.size() + " headers.");
	}

	private void processCache() {
		try {
			processCacheExceptionally();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to read cache.", e);
		}
	}

	private void processCacheExceptionally() throws IOException {
		String content = readBuild(ROOT);
		Partitioner partitioner = new BracketPartitioner(content);
		populateCache(partitioner);
		writeCache();
	}

	private void populateCache(Partitioner partitioner) {
		populateCacheExceptionally(partitioner);
	}

	private void writeCache() {
		try {
			writeCacheExceptionally();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write output.", e);
		}
	}

	private void populateCacheExceptionally(Partitioner partitioner) {
		partitioner.partition()
				.stream()
				.filter(s -> !s.isBlank())
				.map(compiler::parse)
				.forEach(cache::add);
	}

	private String readBuild(Path directory) throws IOException {
		Path build = ensureBuild(directory);
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

	private void writeCacheExceptionally() throws IOException {
		Path out = ROOT.resolve("out.c");
		String headerString = concatHeaders();
		String output = headerString + cache.render();
		Files.writeString(out, output);
	}

	private String concatHeaders() {
		return headers.stream()
				.map(this::formatHeader)
				.collect(Collectors.joining());
	}

	private String formatHeader(String s) {
		return "#include <" + s + ">\n";
	}
}
