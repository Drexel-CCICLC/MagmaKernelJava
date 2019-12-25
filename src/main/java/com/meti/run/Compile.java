package com.meti.run;

import com.meti.Compiler;
import com.meti.unit.MagmaUnit;
import com.meti.unit.UnitCompiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Compile {
	public static final Path OUT = Paths.get("out", "compile.js");
	private static final Compiler compiler = new UnitCompiler(new MagmaUnit());

	private static void compile() {
		try {
			Path build = Paths.get("build");
			if (!Files.exists(build)) Files.createFile(build);
			String value = readFromBuild(build);
			String result = compiler.compile(value);
			if (!Files.exists(OUT.getParent())) {
				Files.createDirectories(OUT.getParent());
			}
			if (!Files.exists(OUT)) {
				Files.createFile(OUT);
			}
			Files.writeString(OUT, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		do {
			String line = scanner.nextLine().toLowerCase().trim();
			if (line.equals("exit")) {
				break;
			} else if (line.equals("compile")) {
				compile();
			}
		} while (true);
	}

	private static String readFromBuild(Path build) throws IOException {
		List<String> lines = Files.readAllLines(build);
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			String[] args = line.trim().split(" ");
			Path child = Paths.get(args[0], Arrays.copyOfRange(args, 1, args.length));
			child = build.resolveSibling(child);
			if (Files.isDirectory(child)) {
				Path childBuild = child.resolve("build");
				if (!Files.exists(childBuild)) {
					Files.createFile(childBuild);
				}
				builder.append(readFromBuild(childBuild));
			} else {
				builder.append(String.join("", Files.readAllLines(child)));
			}
		}
		return builder.toString();
	}
}
