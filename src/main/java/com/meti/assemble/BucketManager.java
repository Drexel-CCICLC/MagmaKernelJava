package com.meti.assemble;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface BucketManager<T> {
    default T atSingle(int index) {
        return at(index).findAny().orElseThrow();
    }

    Stream<T> at(int index);

    Stream<Stream<T>> split(int index, Predicate<? super T> predicate);

    void put(T token);
}
