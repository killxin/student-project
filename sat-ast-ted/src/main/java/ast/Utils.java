package ast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.core.dom.*;

public class Utils {

	public static CompilationUnit createAST(String path) {
		String program = readFromFile(path);
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(program.toCharArray());
		// only setEnvironment can we get bindings from char[]
		parser.setEnvironment(new String[]{} /*classpathEntries*/, 
				new String[]{} /*sourcepathEntries*/, 
				null /*encodings*/, 
				true /*includeRunningVMBootclasspath*/);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null/* IProgressMonitor*/);
	}
	
	public static String readFromFile(String path) {
		StringBuilder r = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String ls = System.getProperty("line.separator");
			String line = in.readLine();
			while (line != null) {
				r.append(line);
				r.append(ls);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r.toString();
	}
}
