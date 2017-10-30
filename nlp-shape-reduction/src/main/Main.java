package main;

import java.io.IOException;
import java.util.Arrays;

import model.Dict;
import model.Word;

public class Main {
	static String path = "dic_ec.txt";
	
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			System.out.println("Usage: java -cp bin main.Main [word sequence]");
			args = new String[]{"unregistered","jrh", "went", "sat","interesting", "helped", "friends", "exercises"};
			System.out.println("using default word sequence: "+Arrays.toString(args));
		}
		Dict dict = new Dict(path);
		for(String arg : args){
			Word word = dict.search(arg);
			if(word != null){
				System.out.println(word);
			} else {
				System.out.println(arg + " is unregistered word!");
			}
		}
	}
}
