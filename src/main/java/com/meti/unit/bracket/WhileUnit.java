package com.meti.unit.bracket;

import com.meti.Compiler;
import com.meti.unit.Unit;

import java.util.Optional;

public class WhileUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		String trimmedInput = input.trim();
		if (trimmedInput.startsWith("while(")) {
			int closingParenthesis = -1;
			int depth = 0;
			char[] charArray = trimmedInput.substring(6).toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c == ')') {
					if (depth == 0) {
						closingParenthesis = i;
						break;
					} else {
						depth--;
					}
				} else if (c == '(') {
					depth++;
				}
			}
			String condition = compiler.compile(trimmedInput.substring(6, closingParenthesis + 6));
			String content = compiler.compile(trimmedInput.substring(closingParenthesis + 7));
			return Optional.of("while(" + condition + ")" + content);
		}
		return Optional.empty();
	}
}
