package main;

import java.io.IOException;

import model.Dict;
import model.Word;

public class Main {
	static String path = "dic_ec.txt";
	
	public static void main(String[] args) throws IOException{
		args = new String[]{"unregistered","jrh", "went", "sat","interesting", "helped", "friends", "exercises"};
		Dict dict = new Dict(path);
		for(String arg : args){
			Word word = dict.search(arg);
			if(word != null){
				System.out.println(word);
			}
		}
	}
}
