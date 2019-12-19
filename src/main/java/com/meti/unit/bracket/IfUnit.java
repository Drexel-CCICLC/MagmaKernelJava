package com.meti.unit.bracket;

import com.meti.Compiler;
import com.meti.unit.Unit;

import java.util.Optional;

public class IfUnit implements Unit {
	@Override
	public Optional<String> parse(String input, Compiler compiler) {
		if (input.startsWith("if(")) {
			int closingParenthesis = -1;
			int depth = 0;
			char[] charArray = input.substring(3).toCharArray();
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
			String condition = compiler.compile(input.substring(3, closingParenthesis + 3));
			String content = compiler.compile(input.substring(closingParenthesis + 4));
			return Optional.of("if(" + condition + ")" + content);
		}
		return Optional.empty();
	}
}
