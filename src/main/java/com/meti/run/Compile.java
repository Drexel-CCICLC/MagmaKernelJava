package com.meti.run;

import com.meti.Compiler;
import com.meti.unit.MagmaUnit;
import com.meti.unit.UnitCompiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Compile {
	public static final Path OUT = Paths.get("out", "compile.js");
	private static final String BUILD = "build";
	private static final Compiler compiler = new UnitCompiler(new MagmaUnit());

	private static final Logger logger = Logger.getAnonymousLogger();
	private static int fileCount = 0;

	private static void compile() {
		try {
			Path build = Paths.get("build");
			if (!Files.exists(build)) Files.createFile(build);
			String value = readFromBuild(build);
			logger.log(Level.INFO, "Located " + fileCount + " files to compile.");
			String result = compiler.compile(value);
			if (!Files.exists(OUT.getParent())) {
				Files.createDirectories(OUT.getParent());
			}
			if (!Files.exists(OUT)) {
				Files.createFile(OUT);
			}
			Files.writeString(OUT, result);
			logger.log(Level.INFO, "Completed compilation.");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed to compile.", e);
		}
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
			logger.log(Level.INFO, "Starting compiler.");
			String line;
			do {
				line = scanner.nextLine().toLowerCase().trim();
				if (line.equals("compile")) {
					compile();
				}
			} while (!line.equals("exit"));
			logger.log(Level.INFO, "Exiting.");
		}
	}

	private static String readFromBuild(Path build) throws IOException {
		List<String> lines = Files.readAllLines(build);
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			String[] args = line.trim().split(" ");
			Path child = Paths.get(args[0], Arrays.copyOfRange(args, 1, args.length));
			child = build.resolveSibling(child);
			if (Files.isDirectory(child)) {
				Path childBuild = child.resolve(BUILD);
				if (!Files.exists(childBuild)) {
					Files.createFile(childBuild);
				}
				builder.append(readFromBuild(childBuild));
			} else {
				fileCount++;
				builder.append(String.join("", Files.readAllLines(child)));
			}
		}
		return builder.toString();
	}
}
