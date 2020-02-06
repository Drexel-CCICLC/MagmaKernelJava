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
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
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
	private final Map<BiPredicate<String, Path>, BiFunction<String, Path, String>> map = Map.of(
			(s, path) -> isHeader(s), (s, path) -> readHeader(s),
			(s, path) -> isSource(s), this::readPath,
			this::isDirectory, this::readDirectory
	);

	public CompileTask(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean canExecute(String line) {
		return line.startsWith("compile");
	}

	@Override
	public void execute(String line) {
		logger.log(Level.INFO, "Compiling sources.");
		clearFields();
		resetHeaders();
		processCache();
	}

	private void clearFields() {
		cache.clear();
		headers.clear();
		declarations.clear();
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

	private boolean isDirectory(String childName, Path parent) {
		Path child = parent.resolve(childName);
		return Files.isDirectory(child);
	}

	private boolean isHeader(String s) {
		return s.endsWith(".h");
	}

	private boolean isSource(String s) {
		return s.endsWith(".magma");
	}

	private void populateCache(Partitioner partitioner) {
		populateCacheExceptionally(partitioner);
	}

	private void populateCacheExceptionally(Partitioner partitioner) {
		partitioner.partition()
				.stream()
				.filter(s -> !s.isBlank())
				.map(compiler::parse)
				.forEach(cache::add);
	}

	private String readDirectory(String trim, Path directory) {
		try {
			return readBuild(directory.resolve(trim));
		} catch (IOException e) {
			return logInvalidPath(trim, directory, e);
		}
	}

	private String readBuild(Path directory) throws IOException {
		Path build = ensureBuild(directory);
		List<String> lines = Files.readAllLines(build);
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			String trim = line.trim();
			if (!trim.isBlank()) {
				String any = resolveChild(directory, trim);
				builder.append(any);
			}
		}
		return builder.toString();
	}

	private String logInvalidPath(String value, Path directory, IOException cause) {
		String trim = value.trim();
		Path path = directory.resolve(trim);
		logger.log(Level.SEVERE, "Failed to read path: " + path, cause);
		return "";
	}

	private Path ensureBuild(Path directory) throws IOException {
		Path build = directory.resolve(BUILD);
		if (!Files.exists(build)) {
			Files.createFile(build);
		}
		return build;
	}

	private String resolveChild(Path directory, String trim) {
		return map.keySet()
				.stream()
				.filter(predicate -> predicate.test(trim, directory))
				.map(map::get)
				.map(function -> function.apply(trim, directory))
				.findAny()
				.orElseThrow(() -> new CompileException("Unknown child: " + trim));
	}

	private String readHeader(String value) {
		headers.add(value.trim());
		return "";
	}

	private String readPath(String value, Path directory) {
		try {
			return readPathExceptionally(value, directory);
		} catch (IOException e) {
			return logInvalidPath(value, directory, e);
		}
	}

	private String readPathExceptionally(String value, Path directory) throws IOException {
		String trim = value.trim();
		Path path = directory.resolve(trim);
		List<String> lines = Files.readAllLines(path);
		return String.join("", lines);
	}

	private void writeCache() {
		try {
			writeCacheExceptionally();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write output.", e);
		}
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
