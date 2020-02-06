package com.meti.node.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringIndexBuffer implements IndexBuffer {
	private final List<Integer> buffer = new ArrayList<>();
	private final String content;

	public StringIndexBuffer(String content, String... sequences) {
		this(content, List.of(sequences));
	}

	public StringIndexBuffer(String content, List<String> sequences) {
		this.content = content;
		if (sequences.isEmpty()) throw new IllegalArgumentException("Cannot have empty sequences.");
		for (int i = 0; i < sequences.size(); i++) {
			int index0 = content.indexOf(sequences.get(i));
			if (i == sequences.size() - 1) {
				buffer.add(index0);
			} else {
				int index1 = content.indexOf(sequences.get(i + 1));
				if (-1 != index1 && index0 > index1) {
					buffer.add(-1);
				} else {
					buffer.add(index0);
				}
			}
		}
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
			int end = buffer.subList(index + 1, buffer.size())
					.stream()
					.mapToInt(Integer::intValue)
					.filter(value -> -1 != value)
					.min()
					.orElse(content.length());
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
