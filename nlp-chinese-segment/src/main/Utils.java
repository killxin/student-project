package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
	static String replace = "[¡°¡±-¡¶¡·¡¾¡¿£¨£©¡¢¡®¡¯]+";
	static String split = "[£¿£¬¡££º£»£¡]+";

	static String[] split(String text) {
		return text.replaceAll(replace, "").split(split);
	}

	public static String[] readFromFile(String path) throws IOException {
		List<String> r = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
		String line = br.readLine();
		while (line != null) {
			String[] strs = split(line);
			if (!line.equals("") && strs.length != 0) {
				r.addAll(Arrays.asList(strs));
			}
			line = br.readLine();
		}
		br.close();
		return r.toArray(new String[] {});
	}

}
