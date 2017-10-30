package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dict {
	public static String splitToken = ",";

	public Map<String, Word> map = new HashMap<>();

	public Dict(String path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
		String line = br.readLine();
		while (line != null) {
			String[] strs = line.split(splitToken);
			if (strs.length != 0) {
				map.put(strs[0], new Word(strs));
			}
			line = br.readLine();
		}
		br.close();
	}

	public String[] fmm(String text) {
		List<String> r = new ArrayList<>();
		int i = 0;
		while (i < text.length()) {
			// java use 2 bytes store char
			String token = String.valueOf(text.charAt(i));
			int off = 1;
			String w = token;
			for (String k : map.keySet()) {
				if (k.startsWith(token) && k.length() > off && (i + k.length()) <= text.length()) {
					String str = text.substring(i, i + k.length());
					if (k.equals(str)) {
						off = k.length();
						w = k;
					}
				}
			}
			r.add(w);
			i += off;
		}
		return r.toArray(new String[] {});
	}

	public String[] rmm(String text) {
		List<String> r = new ArrayList<>();
		int i = text.length() - 1;
		while (i >= 0) {
			// java use 2 bytes store char
			String token = String.valueOf(text.charAt(i));
			int off = 1;
			String w = token;
			for (String k : map.keySet()) {
				if (k.endsWith(token) && k.length() > off && (i - k.length() + 1) >= 0) {
					String str = text.substring(i - k.length() + 1, i + 1);
					if (k.equals(str)) {
						off = k.length();
						w = k;
					}
				}
			}
			r.add(w);
			i -= off;
		}
		Collections.reverse(r);
		return r.toArray(new String[] {});
	}

	public void frmm(String text) {
		String[] fr = fmm(text);
		String[] rr = rmm(text);
		List<Integer[]> records = new ArrayList<>();
		Integer[] r = new Integer[4];
		boolean sign = false;
		int i = -1, j = -1, fl = 0, rl = 0;
		int oldfl = fl, oldrl = rl;
		while (i < fr.length - 1 && j < rr.length - 1) {
			String wf = fr[i + 1];
			String wr = rr[j + 1];
			if (sign == false && wf.length() != wr.length()) {
				sign = true;
			}
			if (fl == rl) {
				if (oldfl != oldrl) {
					r[1] = i + 1;
					r[3] = j + 1;
					records.add(r);
					r = new Integer[4];
				}
				oldfl = fl;
				oldrl = rl;
				fl += fr[++i].length();
				rl += rr[++j].length();
			} else {
				if (oldfl == oldrl) {
					r[0] = i;
					r[2] = j;
				}
				oldfl = fl;
				oldrl = rl;
				if (fl < rl) {
					fl += fr[++i].length();
				} else {
					rl += rr[++j].length();
				}
			}
		}
		if (i < fr.length - 1 || j < rr.length - 1) {
			r[1] = fr.length;
			r[3] = rr.length;
			records.add(r);
			r = new Integer[4];
		}
		if (sign) {
			i = 0;
			System.out.println("{");
			System.out.print("  FMM: ");
			for (Integer[] scope : records) {
				for (; i < scope[0]; i++) {
					System.out.print(fr[i] + " ");
				}
				for (; i < scope[1]; i++) {
					System.out.print("\"" + fr[i] + "\" ");
				}
			}
			for (; i < fr.length; i++) {
				System.out.print(fr[i] + " ");
			}
			System.out.println("");
			System.out.print("  RMM: ");
			i = 0;
			for (Integer[] scope : records) {
				for (; i < scope[2]; i++) {
					System.out.print(rr[i] + " ");
				}
				for (; i < scope[3]; i++) {
					System.out.print("\"" + rr[i] + "\" ");
				}
			}
			for (; i < rr.length; i++) {
				System.out.print(rr[i] + " ");
			}
			System.out.println("");
			System.out.println("}");
		} else {
			for(String ws : fr){
				System.out.print(ws + " ");
			}
			System.out.println("");
		}
	}
}