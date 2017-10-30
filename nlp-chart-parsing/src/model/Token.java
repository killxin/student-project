package model;

/**
 * 1.S->NP VP 2.NP->ART N 3.NP->ART ADJ N 4.VP->V 5.VP->V NP
 **/
public enum Token {
	S("S"), NP("NP"), VP("VP"), ART("ART"), N("N"), ADJ("ADJ"), V("V");

	private String type;

	private Token(String str) {
		this.type = str;
	}

	public static Token[][] cfg() {
		return new Token[][] { { S, NP, VP }, { NP, ART, N },
				{ NP, ART, ADJ, N }, { VP, V }, { VP, V, NP } };
	}

	public static Token str2token(String str) {
		switch (str) {
		case "S":
			return S;
		case "NP":
			return NP;
		case "VP":
			return VP;
		case "ART":
			return ART;
		case "N":
			return N;
		case "ADJ":
			return ADJ;
		case "V":
			return V;
		default:
			System.err.println(str);
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		return type;
	}
}
