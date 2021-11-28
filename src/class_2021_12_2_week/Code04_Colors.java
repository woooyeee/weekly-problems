package class_2021_12_2_week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 来自美团
// 给定一棵多叉树的头节点head
// 每个节点的颜色只会是0、1、2、3中的一种
// 任何两个节点之间的都有路径
// 如果节点a和节点b的路径上，包含全部的颜色，这条路径算达标路径
// (a -> ... -> b)和(b -> ... -> a)算两条路径
// 求多叉树上达标的路径一共有多少？
// 点的数量 <= 10^5
public class Code04_Colors {

	public static class Node {
		public int color;
		public List<Node> nexts;

		public Node(int c) {
			color = c;
			nexts = new ArrayList<>();
		}
	}

	// 暴力方法
	// 为了验证
	public static int test(Node head) {
		if (head == null) {
			return 0;
		}
		HashMap<Node, Node> map = new HashMap<>();
		parentMap(head, null, map);
		List<Node> allNodes = new ArrayList<>();
		for (Node cur : map.keySet()) {
			allNodes.add(cur);
		}
		int ans = 0;
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				if (ok(allNodes.get(i), allNodes.get(j), map)) {
					ans++;
				}
			}
		}
		return ans << 1;
	}

	public static void parentMap(Node cur, Node pre, HashMap<Node, Node> map) {
		if (cur != null) {
			map.put(cur, pre);
			for (Node next : cur.nexts) {
				parentMap(next, cur, map);
			}
		}
	}

	public static boolean ok(Node a, Node b, HashMap<Node, Node> map) {
		HashSet<Node> aPath = new HashSet<>();
		Node cur = a;
		while (cur != null) {
			aPath.add(cur);
			cur = map.get(cur);
		}
		Node lowest = b;
		while (!aPath.contains(lowest)) {
			lowest = map.get(lowest);
		}
		int colors = 1 << lowest.color;
		cur = a;
		while (cur != lowest) {
			colors |= (1 << cur.color);
			cur = map.get(cur);
		}
		cur = b;
		while (cur != lowest) {
			colors |= (1 << cur.color);
			cur = map.get(cur);
		}
		return colors == 15;
	}

	// 正式方法
	public static long colors(Node head) {
		if (head == null) {
			return 0;
		}
		return process(head).all;
	}

	public static class Info {
		public long all;
		public long[] colors;

		public Info() {
			all = 0;
			colors = new long[16];
		}
	}

	public static Info process(Node h) {
		Info ans = new Info();
		int hs = 1 << h.color;
		ans.colors[hs] = 1;
		if (!h.nexts.isEmpty()) {
			int n = h.nexts.size();
			Info[] infos = new Info[n + 1];
			for (int i = 1; i <= n; i++) {
				infos[i] = process(h.nexts.get(i - 1));
				ans.all += infos[i].all;
			}
			long[][] lefts = new long[n + 2][16];
			for (int i = 1; i <= n; i++) {
				for (int status = 1; status < 16; status++) {
					lefts[i][status] = lefts[i - 1][status] + infos[i].colors[status];
				}
			}
			long[][] rights = new long[n + 2][16];
			for (int i = n; i >= 1; i--) {
				for (int status = 1; status < 16; status++) {
					rights[i][status] = rights[i + 1][status] + infos[i].colors[status];
				}
			}
			for (int status = 1; status < 16; status++) {
				ans.colors[status | hs] += rights[1][status];
			}
			ans.all += ans.colors[15] << 1;
			for (int from = 1; from <= n; from++) {
				for (int fromStatus = 1; fromStatus < 16; fromStatus++) {
					for (int toStatus = 1; toStatus < 16; toStatus++) {
						if ((fromStatus | toStatus | hs) == 15) {
							ans.all += infos[from].colors[fromStatus]
									* (lefts[from - 1][toStatus] + rights[from + 1][toStatus]);
						}
					}
				}
			}
		}
		return ans;
	}

	// 为了测试
	public static Node randomTree(int len, int childs) {
		Node head = new Node((int) (Math.random() * 4));
		generate(head, len - 1, childs);
		return head;
	}

	// 为了测试
	public static void generate(Node pre, int restLen, int childs) {
		if (restLen == 0) {
			return;
		}
		int size = (int) (Math.random() * childs);
		for (int i = 0; i < size; i++) {
			Node next = new Node((int) (Math.random() * 4));
			generate(next, restLen - 1, childs);
			pre.nexts.add(next);
		}
	}

	// 为了测试
	public static void printTree(Node head) {
		System.out.print(head.color + " ");
		if (!head.nexts.isEmpty()) {
			System.out.print("( ");
			for (Node next : head.nexts) {
				printTree(next);
				System.out.print(" , ");
			}
			System.out.print(") ");
		}
	}

	// 为了测试
	// 生成高度为9的满5叉树，每个节点的颜色在0~3上随机
	// 这棵树的节点个数已经达到5 * 10^5的规模
	public static Node randomTree() {
		Queue<Node> curq = new LinkedList<>();
		Queue<Node> nexq = new LinkedList<>();
		Node head = new Node((int) (Math.random() * 4));
		curq.add(head);
		for (int len = 1; len < 9; len++) {
			while (!curq.isEmpty()) {
				Node cur = curq.poll();
				for (int i = 0; i < 5; i++) {
					Node next = new Node((int) (Math.random() * 4));
					cur.nexts.add(next);
					nexq.add(next);
				}
			}
			Queue<Node> tmp = nexq;
			nexq = curq;
			curq = tmp;
		}
		return head;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 6;
		int childs = 6;
		int testTime = 3000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			Node head = randomTree(len, childs);
			int ans1 = test(head);
			long ans2 = colors(head);
			if (ans1 != ans2) {
				System.out.println("出错了");
				printTree(head);
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		Node h = randomTree();
		long start = System.currentTimeMillis();
		long ans = colors(h);
		long end = System.currentTimeMillis();
		System.out.println("节点数量达到 5*(10^5) 规模");
		System.out.println("答案 : " + ans + ", 运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}
