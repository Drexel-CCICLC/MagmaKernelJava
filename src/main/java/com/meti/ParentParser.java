package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ParentParser implements Parser {
    protected final Collection<Parser> children;

    public ParentParser(Parser... children) {
        this(Arrays.asList(children));
    }

    public ParentParser(Collection<Parser> children) {
        this.children = children;
    }

    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        return children.stream()
                .map(child -> child.parse(value, compiler))
                .flatMap(Optional::stream)
                .findFirst();
    }
}
