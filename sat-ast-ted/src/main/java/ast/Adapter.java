package ast;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import zhsh.Node;

public class Adapter extends ASTVisitor {
	private Map<ASTNode, Node> map = new HashMap<>();

	@Override
	public void preVisit(ASTNode node) {
		Node treeN = new Node(node);
		if (node.getParent() != null) {
			Node parN = map.get(node.getParent());
			parN.children.add(treeN);
		}
		map.put(node, treeN);
	}

	private Node run(CompilationUnit unit) {
		unit.accept(this);
		return map.get(unit);
	}

	public static Node ast2tree(CompilationUnit unit) {
		return new Adapter().run(unit);
	}
}
