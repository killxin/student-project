package main;

import java.io.IOException;
import java.util.Arrays;

import model.Dict;

public class Main {
	static String path = "dic_ce.txt";
	static String a1 = "实践是检验真理的唯一标准.txt";
	static String a2 = "致橡树.txt";

	public static void main(String[] args) throws IOException {
		if(args.length != 1){
			System.out.println("Usage: java -cp bin main.Main [filepath]");
			args = new String[]{ a2 };
			System.out.println("using default filepath: " + Arrays.toString(args));
		}
		String[] strs = Utils.readFromFile(args[0]);
		Dict dict = new Dict(path);
		for(String str : strs){
			dict.frmm(str);
		}
	}
}
