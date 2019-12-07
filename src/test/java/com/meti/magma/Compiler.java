package com.meti.magma;

import com.meti.assemble.Assembler;
import com.meti.assemble.MagmaAssembler;
import com.meti.assemble.Node;
import com.meti.compile.MagmaTranslator;
import com.meti.compile.Translator;
import com.meti.interpret.Interpreter;
import com.meti.interpret.MagmaInterpreter;
import com.meti.interpret.Statement;
import com.meti.lex.Lexer;
import com.meti.lex.MagmaLexer;
import com.meti.lex.Token;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compiler {
	public static void main(String[] args) {
		new Compiler().run();
	}

	private void run() {
		Lexer lexer = new MagmaLexer();
		Assembler assembler = new MagmaAssembler();
		Interpreter interpreter = new MagmaInterpreter();
		Translator translator = new MagmaTranslator();

		try {
			Path directory = Paths.get("src", "main", "magma");
			List<Path> paths = Files.walk(directory)
					.filter(path -> path.toString().endsWith("magma"))
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());
			for (Path path : paths) {
				try (BufferedReader reader = Files.newBufferedReader(path)) {
					String content = reader.lines().collect(Collectors.joining());
					List<Token<?>> tokens = lexer.parse(content);
					Stream<Node> nodes = assembler.assemble(tokens.stream());
					interpreter.interpret(nodes);
				}
			}

			List<Statement> statements = interpreter.collect();
			String result = translator.translate(statements);
			Path content = directory.resolve("compile.js");
			BufferedWriter writer = Files.newBufferedWriter(content);
			try (PrintWriter print = new PrintWriter(writer)) {
				print.print(result);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
