package zhsh;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

public class Node {
//	public ASTNode label;// wrapper of ASTNode
	public int label;// wrapper of ASTNode Type
	public int index; // preorder index, start with 1
	// note: trees need not be binary
	public ArrayList<Node> children = new ArrayList<Node>();
	public Node leftmost; // used by the recursive O(n) leftmost() function

	public Node() {}
	
	public Node(ASTNode node) {
		this.label = node.getNodeType();
	}

}
