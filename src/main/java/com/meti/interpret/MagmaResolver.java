package com.meti.interpret;

import com.meti.declare.Declarations;
import com.meti.node.Resolver;
import com.meti.node.value.primitive.array.ArrayResolver;
import com.meti.node.value.primitive.character.CharResolver;
import com.meti.node.value.primitive.integer.IntResolver;
import com.meti.node.value.compound.invoke.InvocationResolver;
import com.meti.node.other.AnyResolver;
import com.meti.node.other.VoidResolver;
import com.meti.node.value.primitive.point.PointerResolver;
import com.meti.node.value.primitive.string.StringResolver;
import com.meti.node.bracket.struct.ObjectResolver;
import com.meti.node.bracket.struct.StructResolver;
import com.meti.node.value.compound.variable.VariableResolver;

import java.util.Arrays;
import java.util.Collection;

class MagmaResolver extends ParentResolver {
    MagmaResolver(Declarations declarations) {
        this(
                new PointerResolver(),
                new InvocationResolver(),
                new ArrayResolver(),
                new StructResolver(),
                new StringResolver(),
                new AnyResolver(),
                new VoidResolver(),
                new CharResolver(),
                new IntResolver(),
                new VariableResolver(declarations),
                new ObjectResolver(declarations)
        );
    }

    private MagmaResolver(Resolver... children) {
        this(Arrays.asList(children));
    }

    private MagmaResolver(Collection<Resolver> children) {
        super(children);
    }

}
