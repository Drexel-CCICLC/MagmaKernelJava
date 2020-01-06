package com.meti;

import java.util.Arrays;

public class Compiler {
	String compileFull(String input) {
		String callback = compileTemp(input);
		return "int main(){" + callback + "return 0;}";
	}

	String compileTemp(String input) {
		String callback;
		String trim = input.trim();
		if (trim.isBlank()) callback = "";
		else if (trim.startsWith("val")) {
			int index = trim.indexOf('=');
			String keys = trim.substring(0, index).trim();
			int lastSpace = keys.lastIndexOf(' ');
			String name = keys.substring(lastSpace + 1);
			String value = trim.substring(index + 1);
			callback = "int " + name + "$=" + compileTemp(value) + ";int *" + name + "=&" + name + "$;";
		} else {
			callback = trim;
		}
		return callback;
	}
}