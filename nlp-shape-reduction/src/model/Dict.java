package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import main.Rules;

public class Dict {
	public static String specialToken = "�";

	public Map<String, Word> map = new HashMap<>();

	public Dict(String path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path), "GBK"));
		String line = br.readLine();
		while (line != null) {
			String[] strs = line.split(specialToken + "+");
			if (strs.length % 2 == 1) {
				map.put(strs[0], new Word(strs));
			}
			line = br.readLine();
		}
		br.close();
	}

	public Word search(String str) {
		Word word = map.get(str);
		if (word == null) {
			String[] strs = Rules.reduce(str);
			for (String newStr : strs) {
				if (map.containsKey(newStr)) {
					return map.get(newStr);
				}
			}
			System.out.println(str + " is unregistered word!");
		}
		return word;
	}
}
