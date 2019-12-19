package com.meti.run;

import com.meti.Compiler;
import com.meti.unit.*;
import com.meti.unit.bracket.BracketUnit;
import com.meti.unit.value.NewUnit;
import com.meti.unit.value.ValueUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Compile {
	public static final Path OUT = Paths.get("out", "compile.js");
	public static final Path SOURCE = Paths.get("src", "magma");
	private static final Data data = new SimpleData();
	private static final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new BracketUnit(data),
			new ReturnUnit(),
			new DeclareUnit(data),
			new AssignUnit(data),
			new NewUnit(data),
			new ValueUnit(data)
	));

	public static void main(String[] args) {
		try {
			Path build = SOURCE.resolve("build");
			String value = readFromBuild(build);
			String result = compiler.compile(value);
			Files.writeString(OUT, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readFromBuild(Path build) throws IOException {
		List<String> lines = Files.readAllLines(build);
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			String[] args = line.trim().split(" ");
			Path path = Paths.get(args[0], Arrays.copyOfRange(args, 1, args.length));
			path = build.resolveSibling(path);
			if (Files.isDirectory(path)) {
				builder.append(readFromBuild(path.resolve("build")));
			} else {
				builder.append(String.join("", Files.readAllLines(path)));
			}
		}
		return builder.toString();
	}
}
