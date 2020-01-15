package com.meti;

import com.meti.array.ArrayContentParser;
import com.meti.array.ArraySizeParser;
import com.meti.block.BlockParser;
import com.meti.character.CharParser;
import com.meti.declare.DeclareParser;
import com.meti.integer.IntParser;
import com.meti.invoke.InvocationParser;
import com.meti.operator.OperationParser;
import com.meti.point.DereferenceParser;
import com.meti.point.ReferenceParser;
import com.meti.string.StringParser;
import com.meti.struct.ReturnParser;
import com.meti.struct.StructParser;
import com.meti.variable.VariableParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class MagmaParser extends ParentParser {
    MagmaParser(Declarations declarations, List<Node> functions) {
        this(
                new ReturnParser(),
                new BlockParser(),
                new ArrayContentParser(),
                new ArraySizeParser(),
                new CharParser(),
                new StructParser(declarations, functions),
                new DeclareParser(declarations),
                new IntParser(),
                new InvocationParser(),
                new OperationParser(),
                new DereferenceParser(),
                new ReferenceParser(),
                new StringParser(),
                new VariableParser(declarations)
        );
    }

    MagmaParser(Parser... children) {
        this(Arrays.asList(children));
    }

    MagmaParser(Collection<Parser> children) {
        super(children);
    }

}
