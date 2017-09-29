public class B {
	public int lengthOfLongestSubstring(String s) {
		int n2x = s.length(), ans = 0;
		Map<Character, Integer> map2m = new TreeMap<>();
		for (int j = 0, i = 0; j < n2x; j++) {
			if (map2m.containsKey(s.charAt(j))) {
				i = Math.max(map2m.get(s.charAt(j)), i);
			}
			ans = Math.max(ans, j - i + 1);
			map2m.put(s.charAt(j), j + 1);
		}
		return ans;
	}
}
