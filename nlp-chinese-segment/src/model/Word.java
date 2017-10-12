package model;

import java.util.HashSet;
import java.util.Set;

public class Word {
	public String chinese;
	public Set<String> english = new HashSet<>();
	
	public Word(String[] strs){
		if(strs.length == 0){
			throw new RuntimeException();
		}
		chinese = strs[0];
		for(int i=1;i<strs.length;i++){
			english.add(strs[i]);
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(chinese);
		str.append(" ");
		for(String eng : english){
			str.append(eng);
			str.append(" ");
		}
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chinese == null) ? 0 : chinese.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (chinese == null) {
			if (other.chinese != null)
				return false;
		} else if (!chinese.equals(other.chinese))
			return false;
		return true;
	}
}