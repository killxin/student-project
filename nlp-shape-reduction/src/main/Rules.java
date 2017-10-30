package main;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Dict;

public class Rules {
	static Set<String[]> rules = new HashSet<>();
	static {
		rules.add(new String[] { "(\\w*)s", Dict.specialToken });
		rules.add(new String[] { "(\\w*)es", Dict.specialToken });
		rules.add(new String[] { "(\\w*)ies", Dict.specialToken + "y" });
		rules.add(new String[] { "(\\w*)ing", Dict.specialToken });
		rules.add(new String[] { "(\\w*)ing", Dict.specialToken + "e" });
		rules.add(new String[] { "(\\w*)ying", Dict.specialToken + "ie" });
		rules.add(new String[] { "(\\w*(\\w))\\2ing", Dict.specialToken });
		rules.add(new String[] { "(\\w*)ed", Dict.specialToken });
		rules.add(new String[] { "(\\w*)ed", Dict.specialToken + "e" });
		rules.add(new String[] { "(\\w*)ied", Dict.specialToken + "y" });
		rules.add(new String[] { "(\\w*(\\w))\\2ed", Dict.specialToken });
		rules.add(new String[] { "went", "go" });
		rules.add(new String[] { "gone", "go" });
		rules.add(new String[] { "sat", "sit" });
	}

	public static String[] reduce(String str) {
		Set<String> result = new HashSet<>();
		for (String[] rule : rules) {
			Pattern p = Pattern.compile(rule[0]);
			Matcher m = p.matcher(str);
			if (m.matches()) {
				String value = rule[1].replace(Dict.specialToken, m.group(1));
				result.add(value);
			}
		}
		return result.toArray(new String[] {});
	}

	public static void main(String[] args) {
		String[] strs = reduce("writed");
		for (String str : strs) {
			System.out.println(str);
		}
	}
}
