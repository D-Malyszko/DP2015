import java.awt.Point;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodPrinter {

    public static void Parse(JTree tree, String file) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(file);

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        ast a = new ast();
        a.tree = tree;
        a.node = new DefaultMutableTreeNode();
        
        // visit and print the methods names
        new MethodVisitor().visit(cu, (Object)a);
        
        
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static class MethodVisitor extends VoidVisitorAdapter {
    	
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            
            String name = n.getName();
        	
            ast s = (ast)arg;
            
            ast a = new ast();
            
            a.tree = s.tree;
            
            s.methods.add(a);
         
            int sc = n.getBeginColumn();
            int sr = n.getBeginLine();
            Point start = new Point(sc, sr);
            a.start = start;
            
            int ec = n.getBeginColumn();
            int er = n.getBeginLine();
            Point end = new Point(ec, er);
            a.end = end;
            
            
            ast.LoadMethod(s, a, name);
            
            super.visit(n, a);
            
        }
    	
    	@Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            
            String name = n.getName();
            ast s = (ast)arg;
            ast a = new ast();
            a.tree = s.tree;
            s.classes.add(a);
            int sc = n.getBeginColumn();
            int sr = n.getBeginLine();
            Point start = new Point(sc, sr);
            a.start = start;
            
            int ec = n.getBeginColumn();
            int er = n.getBeginLine();
            Point end = new Point(ec, er);
            a.end = end;
            ast.LoadClass(s, a, name);
            super.visit(n,  a);
        }

    }
}

class ast {
	

	public JTree tree = null;
	
	public DefaultMutableTreeNode node = null;
	
	public String packages = "";
	
	public ArrayList imports  = new ArrayList();
	
	public ArrayList classes = new ArrayList();
	
	public ArrayList methods = new ArrayList();
	
	public ArrayList vars = new ArrayList();
	
	public Point start;
	
	public Point end;
	
	public String text = "";
	
	static public void LoadClass(ast s, ast a, String name){
		
	        DefaultTreeModel model = (DefaultTreeModel)s.tree.getModel();
	        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
	        DefaultMutableTreeNode node = new DefaultMutableTreeNode(a);
	        
	        a.text = "class - " + name;
	        root.add(node);
	        model.reload(root);
	        a.node = node;
		
	}
	static public void LoadMethod(ast s, ast a, String name){
		
        DefaultTreeModel model = (DefaultTreeModel)s.tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(a);
        
        a.text = "method - " + name;
        s.node.add(node);
        model.reload(root);
        a.node = node;
	
}
	
	public String toString(){
		
		return text;
		
	}
	
}

