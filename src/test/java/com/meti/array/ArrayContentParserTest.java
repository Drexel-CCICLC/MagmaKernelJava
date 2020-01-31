package com.meti.array;

import com.meti.Compiler;
import com.meti.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayContentParserTest {

    @Test
    void parse() {
        Parser parser = new ParentParser();
        Resolver resolver = new ParentResolver();
        Compiler compiler = new UnitCompiler(parser, resolver);
        Node node = compiler.parse("val array = Array<Int>{3, 4}");
        assertEquals("int array[]={3,4};", node.render());
    }
}