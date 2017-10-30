package model;

public class Agendum {
	public Token token;
	public int start;
	public int end;
	public Agendum(Token token, int start, int end) {
		this.token = token;
		this.start = start;
		this.end = end;
	}
	@Override
	public String toString() {
		return "Agendum [token=" + token + ", start=" + start + ", end=" + end
				+ "]";
	}
}
