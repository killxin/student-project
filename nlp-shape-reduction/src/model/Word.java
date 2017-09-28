package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Word {
	public String english;
	public Map<String,String> chinese = new HashMap<>();
	
	public Word(String[] strs){
		if(strs.length % 2 == 0){
			throw new RuntimeException();
		}
		english = strs[0];
		for(int i=1;i<strs.length-1;i+=2){
			chinese.put(strs[i], strs[i+1]);
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(english);
		str.append(" ");
		for(Entry<String,String> entry : chinese.entrySet()){
			str.append(entry.getKey());
			str.append(entry.getValue());
			str.append(" ");
		}
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((english == null) ? 0 : english.hashCode());
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
		if (english == null) {
			if (other.english != null)
				return false;
		} else if (!english.equals(other.english))
			return false;
		return true;
	}
}
