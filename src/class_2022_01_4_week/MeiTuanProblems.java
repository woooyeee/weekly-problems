package class_2022_01_4_week;

public class MeiTuanProblems {

}
/*
 * 小美有一个长度为n的序列，为了使得这个序列的和尽量大，她向会魔法的小团进行求助。
 * 小团可以选择序列中至多两个不相交的区间，将区间里的数全都变为原来的10倍。
 * 小团想知道他的魔法最多可以帮助小美将序列的和变大到多少？（也可以不使用魔法保持原来的序列） 我用的预处理数组然后递归，试了几个简单的例子应该是对的，
 * 但是显示内存超限，只通过百分之9，所以这个题应该是要让你用非递归的方法做出来，空间复杂度更重要
 */

/*
 * 小团去参加军训了。军训快要结束了，长官想要把大家一排n个人分成m组，然后让每组分别去参加阅兵仪式。
 * 阅兵仪式上会进行打分，其中有一个奇怪的扣分点是每组的最大差值，即每组最大值减去最小值。
 * 长官想要让这分成的m组总扣分量最小，即这m组分别的极差之和最小。长官正在思索如何安排中，就让小团来帮帮他吧。
 */

/*
 * 给出n个数a[1],a[2],…,a[n]，分别表示一排n个人的身高，以及1个数m，表示要将他们分成m组。
 * 每组要求都是连续的一个区间，比如，可以将某一组安排为第2,3,4,5个人，
 * 但不能安排为第2,3,4,6个人，因为第4个人和第6个人之间有还有一个人，这样的区间是不连续的。 输入 3 2 5 1 2 输出 1
 * 解释：5一组，1和2一组，极差和为1 我用的递归，显示超时，尝试加记忆化搜索但是加错了吧，加了后显示结果错误。最终通过45%
 */
