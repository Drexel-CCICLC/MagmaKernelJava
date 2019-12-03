package com.meti.assemble;

import com.meti.util.PredicateStreamSplitter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ListBucketManager<T> implements BucketManager<T> {
    private final List<Bucket<T>> buckets;

    ListBucketManager(Bucket<T>... buckets) {
        this(List.of(buckets));
    }

    ListBucketManager(List<Bucket<T>> buckets) {
        this.buckets = buckets;
    }

    @Override
    public Stream<T> at(int index) {
        return buckets.get(index).stream();
    }

    @Override
    public Stream<Stream<T>> split(int index, Predicate<? super T> predicate) {
        return new PredicateStreamSplitter<T>(predicate).split(at(index));
    }

    @Override
    public void put(T token) {
        buckets.forEach(bucket -> bucket.append(token));
    }
}
