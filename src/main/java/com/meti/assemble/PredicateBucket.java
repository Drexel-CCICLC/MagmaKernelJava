package com.meti.assemble;

import java.util.ArrayList;
import java.util.Arrays;
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
	public String toString() {
        return "PredicateBucket{" +
                "list=" + list +
                '}';
    }

    @SafeVarargs
    @Override
    public final Bucket<T> appendAll(T... values) {
        Arrays.stream(values).forEach(this::append);
        return this;
    }

    @Override
    public boolean append(T value) {
        boolean result = predicate.test(value);
        if (result) list.add(value);
        return result;
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }
}
