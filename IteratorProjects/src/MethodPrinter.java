import java.awt.Point;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.lexical.Lexeme;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.parser.*;

public class MethodPrinter {

    public static ArrayList Parse(JTree tree, String file) throws Exception {
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
        
        ArrayList L = new ArrayList();
        
        Lexeme lexeme = cu.first();
        
        if(lexeme == null)
        	return L;
        
        L.add(lexeme);
        
        lexeme = lexeme.next();
        
        while(lexeme != null) {
        	
        	L.add(lexeme);
        	
        	lexeme = lexeme.next();
        }
        
        return L;

        
        
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
            
            
            ast.LoadMethod(n, s, a, name);
            
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

      	@Override
        public void visit(VariableDeclarator n, Object arg) {
            
     		
     		//String type = n.getType().toString();
     		
            //List<VariableDeclarator> names = n.getVars();
            
            String dc = n.getId().getName();
            
            ast s = (ast)arg;
            
            s.vars.add(dc);
            
            //ast a = new ast();
            //a.tree = s.tree;
            //s.classes.add(a);
            //int sc = n.getBeginColumn();
            //int sr = n.getBeginLine();
            //Point start = new Point(sc, sr);
            //a.start = start;
            
//            int ec = n.getBeginColumn();
//            int er = n.getBeginLine();
//            Point end = new Point(ec, er);
//            a.end = end;
//            ast.LoadClass(s, a, name);
            super.visit(n,  arg);
        }
    	
    	
     	@Override
        public void visit(VariableDeclarationExpr n, Object arg) {
            
     		
     		String type = n.getType().toString();
     		
            List<VariableDeclarator> names = n.getVars();
            
            String dc = names.get(0).getId().getName();
            
            ast s = (ast)arg;
            
            s.vars.add(dc);
            
            //ast a = new ast();
            //a.tree = s.tree;
            //s.classes.add(a);
            //int sc = n.getBeginColumn();
            //int sr = n.getBeginLine();
            //Point start = new Point(sc, sr);
            //a.start = start;
            
//            int ec = n.getBeginColumn();
//            int er = n.getBeginLine();
//            Point end = new Point(ec, er);
//            a.end = end;
//            ast.LoadClass(s, a, name);
            //super.visit(n,  arg);
        }
    	
       	@Override
        public void visit(FieldDeclaration n, Object arg) {
            
     		
     		String type = n.getType().toString();
     		
            List<VariableDeclarator> names = n.getVariables();
            
            String dc = names.get(0).getId().getName();
            
            ast s = (ast)arg;
            
            s.vars.add(dc);
            
            //super.visit(n,  arg);
            
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
	
	public ArrayList params = new ArrayList();
	
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
	static public void LoadMethod(MethodDeclaration n, ast s, ast a, String name){
		
        DefaultTreeModel model = (DefaultTreeModel)s.tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(a);
        
        a.text = "method - " + name;
        s.node.add(node);
        model.reload(root);
        a.node = node;
        if(n == null)
        	return;
        List<Parameter> list = n.getParameters();
        
        if(list == null)
        	return;
        a.params.addAll(list);
}
	
	public String toString(){
		
		return text;
		
	}
	
}

