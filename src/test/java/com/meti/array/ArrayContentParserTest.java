package com.meti.array;

import com.meti.Compiler;
import com.meti.*;
import com.meti.declare.DeclareParser;
import com.meti.integer.IntParser;
import com.meti.integer.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayContentParserTest {

    @Test
    void parse() {
        Declarations declarations = new Declarations();
        Parser parser = new ParentParser(
                new DeclareParser(declarations),
                new ArrayContentParser(),
                new IntParser()
        );
        Resolver resolver = new ParentResolver(
                new IntResolver()
        );
        Compiler compiler = new UnitCompiler(parser, resolver);
        Node node = compiler.parse("val array = Array<Int>{3, 4}");
        assertEquals("int array[]={3,4};", node.render());
    }
}