package main;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Usage: java -cp bin main.Main [syntaxitems]");
			args = new String[]{ "ART","N,V", "V", "ART", "N" };
			System.out.println("using default syntaxitems: " + Arrays.toString(args));
		}
		ChartParser c = new ChartParser(args);
//		System.out.println(c);
		c.parse();
		System.out.println(c.paint());
	}
}
