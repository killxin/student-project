package eval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sed.StrED;
import zhsh.Zhsh;

class Repo {
	String srcpath;
	String tarpath;
	double degreeByLN;// line number
	double degreeByTED;// tree edit distance
	double degreeBySED;// string edit distance

	public Repo(String srcpath, String tarpath, double degreeByLN,
			double degreeByTED, double degreeBySED) {
		super();
		this.srcpath = srcpath;
		this.tarpath = tarpath;
		this.degreeByLN = degreeByLN;
		this.degreeByTED = degreeByTED;
		this.degreeBySED = degreeBySED;
	}

	@Override
	public String toString() {
		return String.format(
				"Repo[className = %s, dbyLN = %.4f, dByTED = %.4f, dBySED = %.4f]",
				srcpath.substring(srcpath.lastIndexOf("/") + 1), degreeByLN,
				degreeByTED, degreeBySED);
	}
}

public class Evalue {
	static int mode = 1;
	static String root = "/home/rhjiang/workspaceAll/student-project/sat-ast-ted/";
	static String srcdir = root + "src/main/resources/LANG_2_X";
	static String tardir = root + "src/main/resources/LANG_4_X";
	static String di3file = root + "src/main/resources/diff.log";

	public static void main(String[] args) {
		List<Repo> reports = new ArrayList<>();
		Map<String, String> srcMap = new HashMap<>();
		for (String str : sourcePaths(new File(srcdir))) {
			srcMap.put(getClassName(str), str);
		}
		Map<String, String> tarMap = new HashMap<>();
		for (String str : sourcePaths(new File(tardir))) {
			tarMap.put(getClassName(str), str);
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(di3file));
			String line = br.readLine();
			while (line != null) {
				String[] strs = line.trim().split("\\s+");
				if (strs.length == 4) {
					String className = getClassName(strs[0]);
					int lineNum = Integer.parseInt(strs[2]);
					String srcpath = srcMap.get(className);
					String tarpath = tarMap.get(className);
					if (srcpath != null && tarpath != null) {
						System.out
								.println(new Date(System.currentTimeMillis()));
						System.out.println(className + " is comparing");
						double byln = degreeByLN(lineNum, srcpath, tarpath);
						double byted = -1.0, bysed = -1.0;
						if (mode == 0 || mode == 2) {
							System.out.println("calculate degree by ted");
							byted = Zhsh.degreeByTED(srcpath, tarpath);
						}
						if (mode == 1 || mode == 2) {
							System.out.println("calculate degree by sed");
							bysed = StrED.degreeBySED(srcpath, tarpath);
						}
						reports.add(new Repo(srcpath, tarpath, byln, byted,
								bysed));
						System.out
								.println(new Date(System.currentTimeMillis()));
					}
				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Repo r : reports) {
			System.out.println(r);
		}
	}

	static double degreeByLN(int ln, String srcpath, String tarpath) {
		int srcln = 0;
		try {
			BufferedReader br;
			String line;
			br = new BufferedReader(new FileReader(srcpath));
			line = br.readLine();
			while (line != null) {
				srcln++;
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (double) ln / (double)srcln;
	}

	static String getClassName(String str) {
		return str.substring(str.lastIndexOf("/") + 1);
	}

	static List<String> sourcePaths(File file) {
		List<String> result = new ArrayList<>();
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				result.addAll(sourcePaths(f));
			}
		} else {
			if (file.getName().endsWith(".java")) {
				result.add(file.getAbsolutePath());
			}
		}
		return result;
	}
}
