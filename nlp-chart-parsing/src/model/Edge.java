package model;

import java.util.Arrays;

public class Edge {
	public Token[] seq;
	public int pos;
	public int start;
	public int end;
	
	public Edge(Token[] seq, int pos, int start, int end) {
		this.seq = seq;
		this.pos = pos;
		this.start = start;
		this.end = end;
	}

	boolean isActive(){
		return !(seq.length == 1 && pos == 1);
	}

	@Override
	public String toString() {
		return "Edge [seq=" + Arrays.toString(seq) + ", pos=" + pos
				+ ", start=" + start + ", end=" + end + "]";
	}
}
