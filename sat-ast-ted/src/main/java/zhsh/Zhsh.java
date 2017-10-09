package zhsh;

import java.util.ArrayList;

import ast.Utils;

public class Zhsh {

	public static double degreeByTED(String srcpath, String tarpath) {
		Tree a = new Tree(Utils.createAST(srcpath));
		int numA = a.nodeNums(false /* print */);
		Tree b = new Tree(Utils.createAST(tarpath));
		int numB = b.nodeNums(false /* print */);
		int ted = zhangShasha(a, b);
		System.out.println("TED " + ted);
		return 1.0 - (double) ted / (double) (numA + numB);
	}

	// TD[i][j] stores tree distance between node indexOf i and j as root
	static int[][] TD;

	public static int zhangShasha(Tree tree1, Tree tree2) {
		ArrayList<Integer> l1 = tree1.l;
		ArrayList<Integer> keyroots1 = tree1.keyroots;
		ArrayList<Integer> l2 = tree2.l;
		ArrayList<Integer> keyroots2 = tree2.keyroots;

		// space complexity of the algorithm
		TD = new int[l1.size() + 1][l2.size() + 1];

		// solve subproblems
		for (int i1 = 1; i1 < keyroots1.size() + 1; i1++) {
			for (int j1 = 1; j1 < keyroots2.size() + 1; j1++) {
				// main loop calculate TD between keyroots
				int i = keyroots1.get(i1 - 1);
				int j = keyroots2.get(j1 - 1);
				TD[i][j] = treedist(l1, l2, i, j, tree1, tree2);
			}
		}

		return TD[l1.size()][l2.size()];
	}

	private static int treedist(ArrayList<Integer> l1, ArrayList<Integer> l2,
			int i/* root 1 */, int j/* root 2 */, Tree tree1, Tree tree2) {
		// index 0 represent the empty tree
		int[][] forestdist = new int[i + 1][j + 1];

		// costs of the three atomic operations
		int Delete = 1;
		int Insert = 1;
		int Relabel = 1;

		forestdist[0][0] = 0;

		// l1.get(i-1) means the leftmost child of node indexOf i
		for (int i1 = l1.get(i - 1); i1 <= i; i1++) {
			forestdist[i1][0] = forestdist[i1 - 1][0] + Delete;
		}
		for (int j1 = l2.get(j - 1); j1 <= j; j1++) {
			forestdist[0][j1] = forestdist[0][j1 - 1] + Insert;
		}
		for (int i1 = l1.get(i - 1); i1 <= i; i1++) {
			for (int j1 = l2.get(j - 1); j1 <= j; j1++) {
				// i_temp is the node processed before i1(i.e. i1-1),
				// if i1-1 < leftmost node in T(i), i_temp is empty node
				int i_temp = (l1.get(i - 1) > i1 - 1) ? 0 : i1 - 1;
				int j_temp = (l2.get(j - 1) > j1 - 1) ? 0 : j1 - 1;
				if ((l1.get(i1 - 1) == l1.get(i - 1)) && (l2.get(j1 - 1) == l2.get(j - 1))) {
					// both i1 and j1 is not keyroots except current root
					int Cost = tree1.labels.get(i1 - 1)// .getNodeType()
					== tree2.labels.get(j1 - 1)// .getNodeType()
							? 0 : Relabel;
					forestdist[i1][j1] = Math.min(
							Math.min(forestdist[i_temp][j1] + Delete, forestdist[i1][j_temp] + Insert),
							forestdist[i_temp][j_temp] + Cost);
					TD[i1][j1] = forestdist[i1][j1];
				} else {
					// i1(or j2) in right branch of T(i)(or T(j))
					// i1_temp is processed before the leftmost child of T(i1)
					int i1_temp = l1.get(i1 - 1) - 1;
					int j1_temp = l2.get(j1 - 1) - 1;

					// l1.get(i - 1) > i1_temp means l1.get(i1 - 1) == l1.get(i
					// - 1)
					// e.g. i1 in left branch of T(i)
					// else i1 in right branch of T(i)
					int i_temp2 = (l1.get(i - 1) > i1_temp) ? 0 : i1_temp;
					int j_temp2 = (l2.get(j - 1) > j1_temp) ? 0 : j1_temp;

					forestdist[i1][j1] = Math.min(
							Math.min(forestdist[i_temp][j1] + Delete, forestdist[i1][j_temp] + Insert),
							forestdist[i_temp2][j_temp2] + TD[i1][j1]);
				}
			}
		}
		return forestdist[i][j];
	}

	public static String srcpath = "src/main/resources/A.java";
	public static String tarpath = "src/main/resources/B.java";

	public static void main(String[] args) {
		boolean print = false;
		if (args.length == 3 && args[0].equals("--verbose")) {
			print = true;
			srcpath = args[1];
			tarpath = args[2];
		} else if (args.length == 2) {
			srcpath = args[0];
			tarpath = args[1];
		} else {
			System.out.println("Usage: java -jar Zhsh.jar [--verbose] srcpath1 srcpath2");
			System.out.println("Running default examples");
		}
		Tree a = new Tree(Utils.createAST(srcpath));
		int numA = a.nodeNums(print);
		Tree b = new Tree(Utils.createAST(tarpath));
		int numB = b.nodeNums(print);
		int ted = zhangShasha(a, b);
		double degree = 1.0 - (double) ted / (double) (numA + numB);
		System.out.println("========AST Similar Degree========");
		System.out.format("Number of ASTNode in %s = %d\n", srcpath, numA);
		System.out.format("Number of ASTNode in %s = %d\n", tarpath, numB);
		System.out.format("Minimum Tree Edit Distance = %d\n", ted);
		System.out.format("Similar Degree (1.0-ted/(numA+numB)) = %.2f\n", degree);
	}

}
