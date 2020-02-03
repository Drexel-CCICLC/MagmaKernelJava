package com.meti.core;

import com.meti.Cache;
import com.meti.Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CompileTask {
	private static final Path BUILD = Paths.get("build");
	private static final Path ROOT = Paths.get("");
	private final Cache cache = new CollectionCache();
	private final Compiler compiler = new MagmaCompiler(cache);
	private final StringBuilder content = new StringBuilder();
	private final Collection<String> headers = new HashSet<>();

	public static void main(String[] args) {
		new CompileTask().run();
	}

	private void readBuild(Path directory) throws IOException {
		List<String> lines = Files.readAllLines(directory.resolve(BUILD));
		for (String line : lines) {
			String trim = line.trim();
			if (trim.endsWith(".h")) {
				readHeader(trim);
			} else if (trim.endsWith(".magma")) {
				readPath(trim, directory);
			} else {
				readDirectory(trim, directory);
			}
		}
	}

	private void readDirectory(String trim, Path directory) throws IOException {
		readBuild(directory.resolve(trim));
	}

	private void readHeader(String value) {
		headers.add(value.trim());
	}

	private void readPath(String value, Path directory) throws IOException {
		String trim = value.trim();
		Path path = directory.resolve(trim);
		List<String> lines = Files.readAllLines(path);
		String content = String.join("", lines);
		this.content.append(content);
	}

	private void run() {
		try {
			readBuild(ROOT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> partitions = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();
		String content = this.content.toString();
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

		partitions.stream()
				.filter(s -> !s.isBlank())
				.map(compiler::parse)
				.forEach(cache::add);

		try {
			Path out = ROOT.resolve("out.c");
			String headerString = headers.stream()
					.map(s -> "#include <" + s + ">\n")
					.collect(Collectors.joining());
			String output = headerString + cache.render();
			Files.writeString(out, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
