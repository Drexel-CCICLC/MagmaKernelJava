package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.*;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.ints.IntResolver;
import com.meti.node.transform.OperationParser;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.CollectionCache;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectTest {
    @Test
    void test() {
        Declarations declarations = new TreeDeclarations();
        Cache cache = new CollectionCache();
        Unit unit = new StructUnit(declarations, cache);
        Parser parser = new ParentParser(
                unit,
                new DeclareParser(declarations),
                new ReturnParser(),
                new OperationParser(),
                new InvocationParser(declarations),
                new VariableParser(declarations)
        );
        Resolver resolver = new ParentResolver(
                unit,
                new IntResolver(),
                new VariableResolver(declarations),
                new ObjectResolver(declarations)
        );
        Compiler compiler = new UnitCompiler(parser, resolver);
        compiler.parse("""
                class val Some = (Int value) : {
                    val getValue ==> Int : {
                        return value;
                    };
                    val compare = (Some other) => Int :{
                        return value - other.getValue();
                    };
                }
                """);
        Assertions.assertEquals("int _exitCode=0;" +
                "void *_throw=NULL;" +
                "struct Some{int value;};" +
                "int Some_getValue(struct Some Some_){" +
                "return Some_.value;}" +
                "int Some_compare(struct Some other,struct Some Some_){" +
                "return Some_.value-Some_getValue(other);}" +
                "struct Some Some(int value){" +
                "struct Some Some_={value};" +
                "return Some_;}" +
                "int main(){" +
                "return _exitCode;}", cache.render());
    }
}
