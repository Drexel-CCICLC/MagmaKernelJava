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

import java.util.List;
import java.util.stream.Stream;

class MagmaCompiler {
    static String compile(String value) {
        Lexer lexer = new MagmaLexer();
        Assembler assembler = new MagmaAssembler();
        Interpreter interpreter = new MagmaInterpreter();
        Translator translator = new MagmaTranslator();

        List<Token<?>> tokens = lexer.parse(value);
        Stream<Node> nodes = assembler.assemble(tokens.stream());
        interpreter.interpret(nodes);
        List<Statement> statements = interpreter.collect();
        return translator.translate(statements);
    }
}
