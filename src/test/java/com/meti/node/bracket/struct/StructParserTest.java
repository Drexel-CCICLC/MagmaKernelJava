package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.interpret.ParentParser;
import com.meti.interpret.ParentResolver;
import com.meti.node.Node;
import com.meti.node.Parser;
import com.meti.node.Resolver;
import com.meti.node.UnitCompiler;
import com.meti.node.bracket.block.BlockParser;
import com.meti.node.bracket.declare.DeclareParser;
import com.meti.node.value.compound.variable.VariableParser;
import com.meti.node.value.primitive.array.Cache;
import com.meti.node.value.primitive.array.ListedCache;
import com.meti.node.value.primitive.integer.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructParserTest {

    @Test
    void parse() {
        /*

        val Point = (Int x, Int y) => Point :{
            val getX = () => Int :{
                return x;
            };
            val getY = () => Int :{
                return y;
            };
        }

        */

        Declarations declarations = new TreeDeclarations();
        Cache cache = new ListedCache();
        Parser parser = new ParentParser(
                new StructParser(declarations, cache),
                new BlockParser(),
                new DeclareParser(declarations),
                new ReturnParser(),
                new VariableParser(declarations)
        );
        Resolver resolver = new ParentResolver(
                new StructResolver(),
                new IntResolver(),
                new ObjectResolver(declarations)
        );
        Compiler compiler = new UnitCompiler(parser, resolver);
        Node result = compiler.parseSingle("""
                val Point = (Int x, Int y) => Point :{
                    val getX = () => Int :{
                        return x;
                    };
                    val getY = () => Int :{
                        return y;
                    };
                }""");
        assertTrue(result.render().isBlank());
        assertEquals("struct Point{int x;int y;};" +
                "int Point_getX=(struct Point Point_){return Point_.x;}" +
                "int Point_getY=(struct Point Point_){return Point_.y;}", cache.render());
    }
}