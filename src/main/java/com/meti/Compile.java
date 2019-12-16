package com.meti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Compile {
	private static final NodeTree tree = new ListNodeTree();
	private static final Parser parser = new FactoryParser(
			new BlockNodeFactory(),
			new DeclareNodeFactory(tree),
			new StructureNodeFactory(tree),
			new ReturnNodeFactory(),
			new InvocationNodeFactory(tree),
			new OperationNodeFactory(),
			new PrimitiveNodeFactory(),
			new VariableNodeFactory(tree)
	);

	public static void main(String[] args) {
		Path source = Paths.get("src", "magma");
		Path build = source.resolve("build");

		try (BufferedReader reader = Files.newBufferedReader(build)) {
			reader.lines().forEach(s -> {
				if (!s.isBlank()) {
					var child = source.resolve(Arrays.stream(s.split(" "))
							.map(Paths::get)
							.reduce(Path::resolve)
							.orElseThrow());
					compile(child);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		tree.transform();
		var result = tree.compile(new IncrementAliaser());

		Path out = Paths.get("out", "compile.js");
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(out))) {
			writer.print(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void compile(Path child) {
		try (BufferedReader reader = Files.newBufferedReader(child)) {
			var content = reader.lines().collect(Collectors.joining());
			if (!content.isBlank()) {
				parser.parse(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
