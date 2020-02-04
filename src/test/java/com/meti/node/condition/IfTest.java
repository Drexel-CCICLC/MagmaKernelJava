package com.meti.node.condition;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.Resolver;
import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfTest {
    @Test
    void test() {
        Parser parser = new ParentParser(

        );
        Resolver resolver = new ParentResolver(

        );
        Compiler compiler = new UnitCompiler(parser, resolver);
        Node node = compiler.parse("""
                if(true) {
                    val x = 10;
                }; else {
                    val y = 20;
                };
                """);
        assertEquals("if(true){int x=10;}else{int y=20;}", node.render());
    }
}
