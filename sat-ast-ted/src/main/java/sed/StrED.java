package sed;

import zhsh.Zhsh;
import ast.Utils;

public class StrED {
	
	public static double degreeBySED(String srcpath,String tarpath){
		String src = Utils.readFromFile(srcpath).replaceAll("\\s+", " ");
		String tar = Utils.readFromFile(tarpath).replaceAll("\\s+", " ");
		int sed = sed(src,tar);
		System.out.println("SED "+sed);
		return 1.0 - (double)sed/(double)Math.max(src.length(), tar.length());
	}


	static int sed(String s1, String s2) {
		int n1 = s1.length();
		int n2 = s2.length();

		if (n1 == 0)
			return n2;
		else if (n2 == 0)
			return n1;

		int mindis;
		int[][] dis = new int[n1 + 1][];
		for (int i = 0; i < n1 + 1; i++)
			dis[i] = new int[n2 + 1];

		for (int i = 0; i < n1 + 1; i++)
			dis[i][0] = i;

		for (int i = 0; i < n2 + 1; i++)
			dis[0][i] = i;

		for (int i = 1; i < n1 + 1; i++)
			for (int j = 1; j < n2 + 1; j++) {
				if (s1.charAt(i - 1) == s2.charAt(j-1))
					dis[i][j] = dis[i - 1][j - 1];
				else
					dis[i][j] = Math.min(dis[i - 1][j] + 1,
							Math.min(dis[i][j - 1] + 1, dis[i - 1][j - 1] + 1));
			}
		mindis = dis[n1][n2];
		return mindis;
	}
	
	public static String srcpath = "src/main/resources/A.java";
	public static String tarpath = "src/main/resources/B2.java";
	
	public static void main(String[] args) {
		System.out.println("DBySED "+degreeBySED(srcpath,tarpath));
		System.out.println("DByTED "+Zhsh.degreeByTED(srcpath,tarpath));
	}
}
