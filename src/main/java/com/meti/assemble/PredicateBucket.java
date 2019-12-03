package com.meti.assemble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateBucket<T> implements Bucket<T> {
    private final Collection<T> list = new ArrayList<>();
    private final Predicate<? super T> predicate;

    public PredicateBucket(Predicate<? super T> predicate) {
        this.predicate = predicate;
	}

    @Override
    public Bucket<T> append(T value) {
        if (predicate.test(value)) list.add(value);
        return this;
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }
}
