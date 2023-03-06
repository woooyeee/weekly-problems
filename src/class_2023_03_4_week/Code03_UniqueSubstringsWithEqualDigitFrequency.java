package class_2023_03_4_week;

import java.util.HashSet;

// 测试链接 : https://leetcode.cn/problems/unique-substrings-with-equal-digit-frequency/
public class Code03_UniqueSubstringsWithEqualDigitFrequency {

	// 如下的方法是帖子上的一个很骚的方法
	// 其实是不对的，只是可以通过所有当前的测试用例而已
	// 可以构造出让这种方法不通过的例子，原因是这种简陋的hash函数太容易碰撞了
	// 其实这个题，依然是使用DC3算法生成后缀数组来做的
	// 但是很难，具体可以参考LongestChunkedPalindromeDecomposition问题
	// 这里讲一下这种简陋的hash函数，算是一种博闻强识吧
	public static long base = 233;

	public static int equalDigitFrequency(String s) {
		HashSet<Long> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++) {
			int[] cnts = new int[10];
			long cur = 0;
			int maxv = 0, maxc = 0, curc = 0;
			for (int k = i; k < s.length(); k++) {
				cur = cur * base + (s.charAt(k) - '0') + 1;
				int cnt = ++cnts[s.charAt(k) - '0'];
				if (cnt == 1) {
					curc++;
				}
				if (cnt > maxv) {
					maxv = cnt;
					maxc = 1;
				} else if (cnt == maxv) {
					maxc++;
				}
				if (maxc == curc) {
					set.add(cur);
				}
			}
		}
		return set.size();
	}

}
