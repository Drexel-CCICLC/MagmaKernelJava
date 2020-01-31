package com.meti.node.struct;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringIndexBuffer implements IndexBuffer {
	private final List<Integer> buffer;
	private final String content;

	public StringIndexBuffer(String content, Collection<String> sequences) {
		this.content = content;
		this.buffer = sequences.stream()
				.map(content::indexOf)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<String> cutIfPresent(int index) {
		return Optional.of(index)
				.filter(this::isPresent)
				.map(this::cut);
	}

	@Override
	public boolean isPresent(int index) {
		return -1 != buffer.get(index);
	}

	@Override
	public String cut(int index) {
		int start = buffer.get(index);
		int end = contentStream(this.content, index)
				.filter(value -> -1 != value).min()
				.orElseThrow();
		return this.content.substring(start, end);
	}

	private IntStream contentStream(CharSequence content, int index) {
		int length = content.length();
		IntStream lengthStream = IntStream.of(length);
		IntStream bufferStream = bufferStream(index);
		return IntStream.concat(lengthStream, bufferStream);
	}

	private IntStream bufferStream(int index) {
		return buffer.subList(index + 1, buffer.size())
				.stream()
				.mapToInt(Integer::intValue);
	}

	private boolean areAllInvalid() {
		return buffer.stream().allMatch(value -> -1 == value);
	}

	private boolean areNoneZero() {
		return buffer.stream().noneMatch(value -> 0 == value);
	}

	@Override
	public boolean isValid() {
		return !areAllInvalid() && !areNoneZero();
	}
}
