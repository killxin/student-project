package main;

import java.io.IOException;

import model.Dict;

public class Main {
	static String path = "dic_ce.txt";
	static String a1 = "实践是检验真理的唯一标准.txt";
	static String a2 = "致橡树.txt";

	public static void main(String[] args) throws IOException {
		String[] strs = Utils.readFromFile(a1);
		Dict dict = new Dict(path);
		for(String str : strs){
//			String[] ws1 = dict.fmm(str);
//			for(String ws : ws1){
//				System.out.print(ws + " ");
//			}
//			System.out.println("");
//			String[] ws2 = dict.rmm(str);
//			for(String ws : ws2){
//				System.out.print(ws + " ");
//			}
//			System.out.println("");
			dict.frmm(str);
		}
	}
}
