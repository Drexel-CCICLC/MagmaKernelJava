package com.meti.node.transform.cast;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class CastResolver implements Resolver {
	@Override
	public Optional<Type> resolveName(String content, Compiler compiler) {
		return Optional.empty();
	}

	@Override
	public Optional<Type> resolveValue(String content, Compiler compiler) {
		return Optional.of(content)
				.map(String::trim)
				.filter(s -> s.startsWith("<"))
				.map(s -> s.substring(1, findIndex(s) + 1))
				.map(compiler::resolveName);
	}

	private int findIndex(String content) {
		int indexToSplit = -1;
		int depth = 0;
		char[] charArray = content.substring(1).toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if ('>' == c && depth == 0) {
				indexToSplit = i;
			} else {
				if ('<' == c) depth++;
				if ('>' == c) depth--;
			}
		}
		return indexToSplit;
	}
}
