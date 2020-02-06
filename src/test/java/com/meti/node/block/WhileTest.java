package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.Resolver;
import com.meti.node.Node;
import com.meti.node.condition.WhileParser;
import com.meti.node.declare.DeclareParser;
import com.meti.node.primitive.BooleanParser;
import com.meti.node.primitive.IntParser;
import com.meti.node.primitive.IntResolver;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhileTest {
    @Test
    void test() {
        Declarations declarations = new TreeDeclarations();
        Parser parser = new ParentParser(
                new BooleanParser(),
                new WhileParser(),
                new BlockParser(),
                new DeclareParser(declarations),
                new IntParser()
        );
        Resolver resolver = new ParentResolver(
                new IntResolver()
        );
        Compiler compiler = new UnitCompiler(parser, resolver);
        Node node = compiler.parse("""
                while(true) {
                    val x = 10;
                }
                """);
        assertEquals("while(1){int x=10;}", node.render());
    }
}