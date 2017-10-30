package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import model.Agendum;
import model.Edge;
import model.Token;

// e.g. ART V,N N,V ADJ,ART N
public class ChartParser {
	Token[][] seq;

	public ChartParser(String[] strs) {
		seq = new Token[strs.length][];
		for (int i = 0; i < strs.length; i++) {
			String[] ts = strs[i].split(",");
			seq[i] = new Token[ts.length];
			for (int j = 0; j < ts.length; j++) {
				seq[i][j] = Token.str2token(ts[j]);
			}
		}
	}

	Stack<Agendum> agenda = new Stack<>();
	List<Edge> active = new ArrayList<>();
	List<Edge> unactive = new ArrayList<>();
	Token[][] cfg = Token.cfg();

	public void parse() {
		int i = 0;
		while (!(i == seq.length && agenda.empty())) {
			if (agenda.empty()) {
				Token[] next = seq[i++];
				for (Token t : next) {
					agenda.push(new Agendum(t, i - 1, i));
				}
			}
			Agendum agen = agenda.pop();
			List<Edge>  nl = new ArrayList<>();
			for (Token[] rule : cfg) {
				if (rule[1].equals(agen.token)) {
					if (rule.length == 2) {
						agenda.push(new Agendum(rule[0], agen.start, agen.end));
					} else {
						nl.add(new Edge(rule, 1, agen.start, agen.end));
					}
				}
			}
			unactive.add(new Edge(new Token[] { agen.token }, 1, agen.start,
					agen.end));
			for (Edge e : active) {
				if (e.seq[e.pos + 1].equals(agen.token) && e.end == agen.start) {
					if (e.pos + 1 != e.seq.length - 1) {
						nl.add(new Edge(e.seq, e.pos + 1, e.start, agen.end));
					} else {
						agenda.add(new Agendum(e.seq[0], e.start, agen.end));
					}
				}
			}
			active.addAll(nl);
		}
	}
	
	public String paint(){
		StringBuilder r = new StringBuilder();
		r.append("graph G {\n");
		int i =0;
		for(;i<seq.length;i++){
			r.append(i+"[label=\""+ i+"\"]\n");
		}
		r.append(i+"[label=\""+ i+"\"]\n");
//		i =0;
//		for(Token[] t : seq){
//			r.append(i+" -- "+(++i)+"[label=\""+Arrays.toString(t)+"\",color=red]\n");
//		}
		for(Edge e : active){
			String label = Arrays.toString(e.seq) + ":" +e.pos;
			r.append(e.start+" -- "+e.end+"[label=\""+label+"\",color=yellow]\n");
		}
		for(Edge e : unactive){
			String label = Arrays.toString(e.seq) + ":" +e.pos;
			r.append(e.start+" -- "+e.end+"[label=\""+label+"\",color=green]\n");
		}
		r.append("}\n");
		return r.toString();
	}

	@Override
	public String toString() {
		String r = "";
		for (Token[] strs : seq) {
			r += Arrays.toString(strs) + ",";
		}
		return r;
	}
	
}
