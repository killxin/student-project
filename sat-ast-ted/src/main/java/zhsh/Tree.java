package zhsh;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

import ast.Adapter;

public class Tree {
	Node root = new Node();
	
	// function l() which gives the leftmost child
	ArrayList<Integer> l = new ArrayList<>();
	
	// list of keyroots, i.e., nodes with a left child and the tree root
	ArrayList<Integer> keyroots = new ArrayList<>();
	
	// list of the labels of the nodes used for node comparison
	ArrayList<Integer> labels = new ArrayList<>();

	public Tree(CompilationUnit unit) {
		root = Adapter.ast2tree(unit);
		index();
		l();
		keyroots();
		traverse();
	}

	boolean print = false;
	
	public int nodeNums(boolean print) {
		this.print = print;
		if(print){
			System.out.println("========ASTPrinter========");
		}
		return nodeNums(root, 0, 0);
	}

	public int nodeNums(Node node, int space, int num) {
		if(print){
			for (int i = 0; i < space; i++)
				System.out.print(" ");
			System.out.println(node);
		}
		num ++;
		for (int i = 0; i < node.children.size(); i++) {
			num = nodeNums(node.children.get(i), space + 2, num);
		}
		return num;
	}

	private void index() {
		// index each node in the tree according to traversal method
		index(root, 0);
	}

	private int index(Node node, int index) {
		for (int i = 0; i < node.children.size(); i++) {
			index = index(node.children.get(i), index);
		}
		index++;
		node.index = index;
		return index;
	}

	private void traverse() {
		// put together an ordered list of node labels of the tree
		traverse(root, labels);
	}

	private ArrayList<Integer> traverse(Node node,
			ArrayList<Integer> labels) {
		for (int i = 0; i < node.children.size(); i++) {
			labels = traverse(node.children.get(i), labels);
		}
		labels.add(node.label);
		return labels;
	}

	private void l() {
		// put together a function which gives l()
		leftmost();
		l = l(root, new ArrayList<Integer>());
	}

	private ArrayList<Integer> l(Node node, ArrayList<Integer> l) {
		for (int i = 0; i < node.children.size(); i++) {
			l = l(node.children.get(i), l);
		}
		l.add(node.leftmost.index);
		return l;
	}

	private void leftmost() {
		leftmost(root);
	}

	private void leftmost(Node node) {
		if (node == null)
			return;
		for (int i = 0; i < node.children.size(); i++) {
			leftmost(node.children.get(i));
		}
		if (node.children.size() == 0) {
			node.leftmost = node;
		} else {
			node.leftmost = node.children.get(0).leftmost;
		}
	}

	private void keyroots() {
		// calculate the keyroots
		for (int i = 0; i < l.size(); i++) {
			int flag = 0;
			for (int j = i + 1; j < l.size(); j++) {
				if (l.get(j) == l.get(i)) {
					flag = 1;
				}
			}
			if (flag == 0) {
				// i + 1 is the number of keyroots
				this.keyroots.add(i + 1);
			}
		}
	}
	
	public Integer[] preorder(){
		List<Integer> list = new ArrayList<>();
		list = preorder(root,list);
		return list.toArray(new Integer[]{});
	}
	
	private List<Integer> preorder(Node node,List<Integer> list) {
		for (int i = 0; i < node.children.size(); i++) {
			list = preorder(node.children.get(i),list);
		}
		list.add(node.label);
		return list;
	}

}
