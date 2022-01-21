package class_2022_02_2_week;

public class Problem_2029_StoneGameIX {

	public static boolean stoneGameIX(int[] stones) {
		int[] counts = new int[3];
		for (int num : stones) {
			counts[num % 3]++;
		}
		return counts[0] % 2 == 0 ? counts[1] != 0 && counts[2] != 0 : Math.abs(counts[1] - counts[2]) > 2;
	}

}
