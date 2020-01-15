package com.meti;

import com.meti.array.ArrayContentParser;
import com.meti.array.ArraySizeParser;
import com.meti.character.CharParser;
import com.meti.declare.DeclareParser;
import com.meti.integer.IntParser;
import com.meti.invoke.InvocationParser;
import com.meti.operator.OperationParser;
import com.meti.point.DereferenceParser;
import com.meti.point.ReferenceParser;
import com.meti.string.StringParser;
import com.meti.struct.StructParser;

import java.util.Arrays;
import java.util.Collection;

class MagmaParser extends ParentParser {
    MagmaParser() {
        this(
                new ArrayContentParser(),
                new ArraySizeParser(),
                new CharParser(),
                new StructParser(),
                new DeclareParser(),
                new IntParser(),
                new InvocationParser(),
                new OperationParser(),
                new DereferenceParser(),
                new ReferenceParser(),
                new StringParser()
        );
    }

    MagmaParser(Parser... children) {
        this(Arrays.asList(children));
    }

    MagmaParser(Collection<Parser> children) {
        super(children);
    }

}
