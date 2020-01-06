package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Compiler {
	String compileAll(String input) {
		String callback = compileOnly(input);
		return "int main(){" + callback + "return 0;}";
	}

	String compileOnly(String input) {
		String callback;
		String trim = input.trim();
		if (trim.isBlank()) callback = "";
		else if (trim.startsWith("{")) {
			String content = trim.substring(1, trim.length() - 1);
			String collect = Arrays.stream(content.split(";"))
					.map(this::compileOnly)
					.collect(Collectors.joining());
			return "{" + collect + "}";
		} else if (trim.contains("+")) {
			int operation = trim.indexOf('+');
			String first = trim.substring(0, operation);
			String last = trim.substring(operation + 1);
			return compileOnly(first) + "+" + compileOnly(last);
		} else if (trim.startsWith("val")) {
			int index = trim.indexOf('=');
			String keys = trim.substring(0, index).trim();
			int lastSpace = keys.lastIndexOf(' ');
			String name = keys.substring(lastSpace + 1);
			String value = trim.substring(index + 1);
			callback = "int " + name + "$=" + compileOnly(value) + ";int *" + name + "=&" + name + "$;";
		} else if (isInt(trim)) {
			callback = trim;
		} else {
			throw new ParseException("Failed to parse: " + trim);
		}
		return callback;
	}

	private boolean isInt(String value) {
		boolean isInt;
		try {
			Integer.parseInt(value);
			isInt = true;
		} catch (Exception e) {
			e.printStackTrace();
			isInt = false;
		}
		return isInt;
	}
}