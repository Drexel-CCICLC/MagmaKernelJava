package com.meti.run;

import com.meti.Compiler;
import com.meti.unit.*;
import com.meti.unit.bracket.BracketUnit;
import com.meti.unit.value.NewUnit;
import com.meti.unit.value.ValueUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Compile {
	public static final Path SOURCE = Paths.get("src", "magma");
	public static final Path OUT = Paths.get("out", "compile.js");

	private static final Data data = new SimpleData();
	private static final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new BracketUnit(data),
			new ReturnUnit(),
			new DeclareUnit(data),
			new NewUnit(data),
			new ValueUnit(data)
	));

	public static void main(String[] args) {
		Path build = SOURCE.resolve("build");
		try {
			String value = Files.readAllLines(build)
					.stream()
					.map(String::trim)
					.map(Compile::read)
					.collect(Collectors.joining());
			String result = compiler.compile(value);
			Files.writeString(OUT, result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String read(String string) {
		try {
			return String.join("", Files.readAllLines(SOURCE.resolve(string)));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
