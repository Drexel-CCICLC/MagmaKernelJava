package com.meti.node.struct;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StringIndexBuffer implements IndexBuffer {
	private final List<Integer> buffer;
	private final String content;

	public StringIndexBuffer(String content, String... sequences) {
		this(content, List.of(sequences));
	}

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
		if (index == buffer.size() - 1) {
			return content.substring(buffer.get(index));
		} else {
			int start = buffer.get(index);
			int i = 0;
			int end;
			do {
				i++;
				end = buffer.get(index + i);
			} while (-1 == end);
			return end > start ?
					content.substring(start, end) :
					content.substring(start);
		}
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
