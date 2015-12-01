 
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
 
public class TreeExample extends JFrame
{
    private JTree tree;
    public TreeExample() throws Exception
    {
    	
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    	
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Application.java");
 
        tree = new JTree(root);
        add(tree);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Examples");
        this.setPreferredSize(new Dimension(500, 600));
        this.pack();
        this.setVisible(true);
         
    }
     
    public static void Parse(JTree tree) throws Exception{
    	
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Application.java");
    	
    	DefaultTreeModel model = new DefaultTreeModel(root);
    	
    	tree.setModel(model);
    	
    	MethodPrinter.Parse(tree,"Application.java");
    }
    
    public static ArrayList Parse(JTree tree, String file) throws Exception{
    	
  
    	
    	ArrayList L = MethodPrinter.Parse(tree, file);
    	
    	return L;
    	
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					new TreeExample();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }       
}