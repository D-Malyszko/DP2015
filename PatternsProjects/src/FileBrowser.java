import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.text.Highlighter;
import javax.swing.filechooser.FileSystemView;
import javax.imageio.ImageIO;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.commons.io.IOUtils;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.lexical.Lexeme;
import com.github.javaparser.ast.lexical.LexemeKind;
import com.sun.org.apache.bcel.internal.util.ClassPath.ClassFile;

import syntaxhighlight.Eventer;
import syntaxhighlight.SyntaxHighlighter;
import syntaxhighlighter.SyntaxHighlighterParser;
import syntaxhighlighter.brush.BrushCSharp;
import syntaxhighlighter.brush.BrushJava;
import syntaxhighlighter.example.Example;
import syntaxhighlighter.theme.ThemeEclipse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.net.URL;
// stackexchange source code.....
class FileBrowser {

    /** Title of the application */
    public static final String APP_TITLE = "File Browser";
    /** Used to open/edit/print files. */
    private Desktop desktop;
    /** Provides nice icons and names for files. */
    private FileSystemView fileSystemView;

    /** currently selected File. */
    public File currentFile;

    /** Main GUI container */
    private JPanel gui;

    /** File-system tree. Built Lazily */
    private JTree tree;
    private DefaultTreeModel treeModel;

    /** Directory listing */
    private JTable table;
    private JProgressBar progressBar;
    /** Table model for File[]. */
    private FileTableModel fileTableModel;
    private ListSelectionListener listSelectionListener;
    private boolean cellSizesSet = false;
    private int rowIconPadding = 6;

    /* File controls. */
    private JButton openFile;
    private JButton printFile;
    private JButton editFile;

    /* File details. */
    private JLabel fileName;
    private JTextField path;
    private JLabel date;
    private JLabel size;
    private JCheckBox readable;
    private JCheckBox writable;
    private JCheckBox executable;
    private JRadioButton isDirectory;
    private JRadioButton isFile;

    /* GUI options/containers for new File/Directory creation.  Created lazily. */
    private JPanel newFilePanel;
    private JRadioButton newTypeFile;
    private JTextField name;

    public FlyweightFactory factory = new FlyweightFactory();
    
    public JPanel panel;
    
    static class OSValidator {

        private static String OS = System.getProperty("os.name").toLowerCase();

     
        public static boolean isWindows() {
            return (OS.indexOf("win") >= 0);
        }

        public static boolean isMac() {
            return (OS.indexOf("mac") >= 0);
        }

        public static boolean isUnix() {
            return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
        }

        public static boolean isSolaris() {
            return (OS.indexOf("sunos") >= 0);
        }
        public static String getOS(){
            if (isWindows()) {
                return "win";
            } else if (isMac()) {
                return "osx";
            } else if (isUnix()) {
                return "uni";
            } else if (isSolaris()) {
                return "sol";
            } else {
                return "err";
            }
        }

    }
    

    
    public void LoadFiles(String path){
    	
    	
    	tree.setExpandsSelectedPaths(true);
    	
    	String []paths = path.split("/");
   	
    	String file = "";
    	
    	for(int i = 1; i < paths.length; i++){
    		
    	
    		if(i == 1)
    		file = "/" + paths[1];
    		else 
    		file = file + "/" + paths[i];
    		
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)findTreeNode(new File(file));
    		
    		TreePath tp = findTreePath(new File(file));
    		
    		if(node == null){
    			
    			
    			return;
    			
    			
    		}
    		else {

    			tree.repaint();
    			
    			LoadNodes((DefaultMutableTreeNode)node);
    			setFileDetails((File)node.getUserObject());

    			tree.repaint();
    			
    			tree.expandPath(tp);
    			
    			tree.setSelectionPath(tp);
    			
    		}
    		
    	}
    	
    }

   public void LoadFileLX(String path){
    	
    	
    	tree.setExpandsSelectedPaths(true);
    	
    	String []paths = path.split("/");

    	
    	
    	String file = "";
    	
    	
    	
    	
    	
    	for(int i = 1; i < paths.length; i++){
    		
    	
    		if(i == 1)
    		file = "/" + paths[1];
    		else 
    		file = file + "/" + paths[i];
    		
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)findTreeNode(new File(file));
    		
    		TreePath tp = findTreePath(new File(file));
    		
    		if(node == null){
    			
    			
    			return;
    			
    			
    		}
    		else {

    			tree.expandPath(tp);
    			
    			tree.setSelectionPath(tp);

    			tree.repaint();
    			
    			showChildren((DefaultMutableTreeNode)node);
    			setFileDetails((File)node.getUserObject());

    			tree.repaint();
    			
    		}
    		
    		
    		
    	}
    	
    }
    
    
    public void LoadFile(String path){
    	
    	
    	tree.setExpandsSelectedPaths(true);
    	
    	String []paths = path.split("\\\\");
   	
    	String file = "";
      	
    	for(int i = 0; i < paths.length; i++){
    		
    	
    		if(i == 0)
    		file = paths[0];
    		else 
    		file = file + "\\\\" + paths[i];
    		
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)findTreeNode(new File(file));
    		
    		TreePath tp = findTreePath(new File(file));
    		
    		if(node == null){
    			
    			
    			return;
    			
    			
    		}
    		else {

    			tree.expandPath(tp);
    			
    			tree.setSelectionPath(tp);

    			tree.repaint();
    			
    			showChildren((DefaultMutableTreeNode)node);
    			setFileDetails((File)node.getUserObject());

    			tree.repaint();

    		}
   		
    	}
    	
    }

    static public File[] GetFiles(String folders){
    	
    	
    	File folder = new File(folders);
    	File[] listOfFiles = folder.listFiles();

    	    for (int i = 0; i < listOfFiles.length; i++) {
    	      if (listOfFiles[i].isFile()) {
    	        System.out.println("File " + listOfFiles[i].getName());
    	      } else if (listOfFiles[i].isDirectory()) {
    	        System.out.println("Directory " + listOfFiles[i].getName());
    	      }
    	    }
    	
    	return listOfFiles;
    }
    
    public void LoadAst(ast a){
    	
    	
    	
    	 DefaultTreeModel model = (DefaultTreeModel)pls.getModel();
    	 
    	 DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
    	 
    	 root.removeAllChildren();
    	 
         for(Object s : a.params) {
         
         Parameter g = (Parameter)s;
        	 
         DefaultMutableTreeNode node = new DefaultMutableTreeNode(g.getType().toString() + " - " + g.getId().getName());
    	
    	 root.add(node);
         
         }

         for(Object s : a.vars) {
             
             String g = (String)s;
            	 
             DefaultMutableTreeNode node = new DefaultMutableTreeNode(g);
        	
        	 root.add(node);
             
             }
         
         
         model.reload(root);
    }
    
    
    JPanel up = null;
    
    JPanel dw = null;

    JTree pls = null;
    
    JTree lbs = null;
    
    public void LoadParsePanel(){
    	
    	up = new JPanel();
    	
    	dw = new JPanel();

        DefaultMutableTreeNode model = new DefaultMutableTreeNode("");
        
        pls = new JTree(model);
        
        JScrollPane scrolls = new JScrollPane(pls);
       
        DefaultMutableTreeNode modeldw = new DefaultMutableTreeNode("");
        
        lbs = new JTree(modeldw);
 
        
        
        
        JScrollPane scrollsdw = new JScrollPane(lbs);
        
    	parsePanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrolls, scrollsdw);
    	
    	parsePanel.setResizeWeight(0.5);
       
    	splitPanel.setDividerLocation(locs);
    	
    	
        TreeSelectionListener treeSelectionListeners = new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent tse){
           
            	
              	if(parsePanel == null)
            			LoadParsePanel();
            	
            	
                DefaultMutableTreeNode node =     (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                
                Object object = node.getUserObject();
                        
            	
            	if(object instanceof java.util.jar.JarEntry){
                	
                	
                	java.util.jar.JarEntry jarFile = (java.util.jar.JarEntry)object;
                	
                	String file = jarFile.toString();
                	
                	String entry = "dest" + "\\" + file;
                	
                	String content = flyweight.LoadJar(entry);
                
                	//String asm = flyweight.loadClass(entry);
                	
                	try{
                	flyweight.Display(entry, panel, trees, lbs, content /*+ "\n" + asm*/);
                	}
                	catch(Exception e){};
                	
                }
            	
            }
        };
 
        lbs.addTreeSelectionListener(treeSelectionListeners);

    	
    	
    	
    	
    	LoadInfoPanel();
    	
    }

    JPanel ups = null;
    
    JPanel dws = null;

    JTree plsp = null;
    
    JTree lbsp = null;
    
    public void LoadInfoPanel(){
    	
    	ups = new JPanel();
    	
    	dws = new JPanel();

        DefaultMutableTreeNode model = new DefaultMutableTreeNode("");
        
        plsp = new JTree(model);
        
        JScrollPane scrolls = new JScrollPane(plsp);
       
        DefaultMutableTreeNode modeldw = new DefaultMutableTreeNode("");
        
        lbsp = new JTree(modeldw);
 
        JScrollPane scrollsdw = new JScrollPane(lbsp);
        
    	infoPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrolls, scrollsdw);
    	
    	infoPanel.setResizeWeight(0.5);
       
    	infoPanel.setDividerLocation(locs);
    	
    }

    
    public Integer search = 0;
    
  	public int locs = 250;
    
	public void toggledefaults(){
		
		
		if(parsePanel == null)
			LoadParsePanel();
		
		if(search == 0){
		
			locs = splitPanel.getDividerLocation();
			
			splitPanel.setLeftComponent(parsePanel);
			
			splitPanel.setDividerLocation(locs);
			
			splitPanel.repaint();
			
			search = 1;
		}
		else if(search == 1) {
			
			locs = splitPanel.getDividerLocation();
			
			splitPanel.setLeftComponent(infoPanel);
			
			splitPanel.setDividerLocation(locs);
			
			splitPanel.repaint();
		
			search = 2;
		}
	else if(search == 2) {
			
			locs = splitPanel.getDividerLocation();
			
			splitPanel.setLeftComponent(splitPane);
			
			splitPanel.setDividerLocation(locs);
			
			splitPanel.repaint();
		
			search = 0;
		}
		
	}

   
    JTree trees = null;
    
    JSplitPane splitPane = null;
    		
    JSplitPane splitPanel = null;
    
    JSplitPane parsePanel = null;
    
    JSplitPane infoPanel = null;
    
    public Container getGui(JFrame frame) {
        if (gui==null) {
            gui = new JPanel(new BorderLayout(3,3));
            gui.setBorder(new EmptyBorder(5,5,5,5));

            JMenuBar toolBar = new JMenuBar();
 
            frame.setJMenuBar(toolBar);
            
            fileSystemView = FileSystemView.getFileSystemView();
            desktop = Desktop.getDesktop();

            JPanel detailView = new JPanel(new BorderLayout(3,3));

            table = new JTable();
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoCreateRowSorter(true);
            table.setShowVerticalLines(false);

            listSelectionListener = new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    int row = table.getSelectionModel().getLeadSelectionIndex();
                    setFileDetails( ((FileTableModel)table.getModel()).getFile(row) );
                }
            };
            table.getSelectionModel().addListSelectionListener(listSelectionListener);
            JScrollPane tableScroll = new JScrollPane(table);
            Dimension d = tableScroll.getPreferredSize();
            tableScroll.setPreferredSize(new Dimension((int)d.getWidth(), (int)d.getHeight()/2));
            detailView.add(tableScroll, BorderLayout.CENTER);

            // the File tree
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            treeModel = new DefaultTreeModel(root);

            TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent tse){
                    DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                    showChildren(node);
                    setFileDetails((File)node.getUserObject());
                }
            };
           
            // show the file system roots.
            File[] roots = fileSystemView.getRoots();
            for (File fileSystemRoot : roots) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                root.add( node );
                File[] files = fileSystemView.getFiles(fileSystemRoot, true);
                for (File file : files) {
                    if (file.isDirectory()) {
                        node.add(new DefaultMutableTreeNode(file));
                    }
                }
                //
            }

            tree = new JTree(treeModel);
            tree.setRootVisible(false);
            tree.addTreeSelectionListener(treeSelectionListener);
            tree.setCellRenderer(new FileTreeCellRenderer());
            tree.expandRow(0);
            JScrollPane treeScroll = new JScrollPane(tree);

            // as per trashgod tip
            tree.setVisibleRowCount(15);

            Dimension preferredSize = treeScroll.getPreferredSize();
            Dimension widePreferred = new Dimension(
                200,
                (int)preferredSize.getHeight());
            treeScroll.setPreferredSize( widePreferred );

            // details for a File
            JPanel fileMainDetails = new JPanel(new BorderLayout(4,2));
            //fileMainDetails.setBorder(new EmptyBorder(0,6,0,6));

            JPanel fileDetailsLabels = new JPanel(new GridLayout(0,1,2,2));
            //fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

            JPanel fileDetailsValues = new JPanel(new GridLayout(0,1,2,2));
            //fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

            fileDetailsLabels.add(new JLabel("File", JLabel.TRAILING));
            fileName = new JLabel();
            fileDetailsValues.add(fileName);
            fileDetailsLabels.add(new JLabel("Path/name", JLabel.TRAILING));
            path = new JTextField(5);
            path.setEditable(false);
            fileDetailsValues.add(path);
            fileDetailsLabels.add(new JLabel("Last Modified", JLabel.TRAILING));
            date = new JLabel();
            fileDetailsValues.add(date);
            fileDetailsLabels.add(new JLabel("File size", JLabel.TRAILING));
            size = new JLabel();
            fileDetailsValues.add(size);
            fileDetailsLabels.add(new JLabel("Type", JLabel.TRAILING));

            JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING,4,0));

            isDirectory = new JRadioButton("Directory");
            flags.add(isDirectory);

            isFile = new JRadioButton("File");
            flags.add(isFile);
            fileDetailsValues.add(flags);
         
            JButton locateFile = new JButton("File content");
            locateFile.setMnemonic('l');

            JButton recentFile = new JButton("Outline");
            
            JButton javaJre = new JButton("JDK"); 
           
            JButton recentFiles = new JButton("Recent");
            
            JPanel fileView = new JPanel(new BorderLayout(3,3));

            detailView.add(fileView, BorderLayout.SOUTH);

            splitPane = new JSplitPane(
       
            		JSplitPane.VERTICAL_SPLIT,
                treeScroll,
                detailView);
            
            JPanel simpleOutput = new JPanel(new BorderLayout(3,3));
            progressBar = new JProgressBar();
            simpleOutput.add(progressBar, BorderLayout.EAST);
            progressBar.setVisible(false);

                        
            panel = new JPanel(new BorderLayout());
           
            panel.setVisible(true);
         
            DefaultMutableTreeNode model = new DefaultMutableTreeNode("");
            trees = new JTree(model);
            
            JScrollPane treeScrolls = new JScrollPane(trees);
            
            JSplitPane splitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, treeScrolls);
                    
            splitPanes.setResizeWeight(0.5);
            
            splitPanes.setDividerLocation(400);
            
            splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, splitPanes);
            
            gui.add(splitPanel, BorderLayout.CENTER);
            
            splitPanel.setDividerLocation(200);
           
            TreeSelectionListener treeSelectionListeners = new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent tse){
                	
                	if(parsePanel == null)
            			LoadParsePanel();
                	
                    DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                   
                        Object object = node.getUserObject();
                    
                        if(object instanceof ast) {
                        
                    	ast a = (ast)node.getUserObject();
                    	
                    	Point s = a.start;
                    	Point e = a.end;
                    	
                    	List<Integer> hs = new ArrayList<Integer>();
                    	
                    	for(int i = s.y; i <= e.y; i++)
                    	hs.add(i);
              	
                    	flyweight.highlighter.setHighlightedLineList(hs);
                     	                   	
                    	flyweight.highlighter.validate();
                    	
                    	flyweight.highlighter.repaint();
                    	
                    	int H = flyweight.highlighter.highlighter.GetLineHeight();
                    	
                    	flyweight.highlighter.scrollTo((H *(s.y - 4)));
                    	
                    	flyweight.highlighter.highlighter.setCaretColor(Color.BLACK);
                    	
                    	flyweight.highlighter.repaint();
                    	
                    	LoadAst(a);
                    	
                        }
                        else if(object instanceof java.util.jar.JarEntry){
                        	
                        	
                        	java.util.jar.JarEntry jarFile = (java.util.jar.JarEntry)object;
                        	
                        	String file = jarFile.toString();
                        	
                        	String entry = "dest" + "\\" + file;
                        	
                        	String content = flyweight.LoadJar(entry);
                        
                        	String asm = flyweight.loadClass(entry);
                        	
                        	try{
                        	flyweight.Display(file, panel, trees, lbs, content + "\n" + asm);
                        	}
                        	catch(Exception e){};
                        	
                        }
                }
            };
     
            trees.addTreeSelectionListener(treeSelectionListeners);
            
            locateFile.addActionListener(new ActionListener(){
            	
                public void actionPerformed(ActionEvent ae) {
                    try {
                    	                    	
                        System.out.println("Locate: " + currentFile.getParentFile());
                                                                
                        String file = currentFile.getAbsolutePath();
                        
                        String exts = file.substring(file.lastIndexOf("."));
                        
                        flyweight = factory.get(exts);
                        
                        flyweight.Display(file, panel, trees,lbs, "");
                        
                        
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(locateFile);
           
            recentFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
                      
                    	toggledefaults();
                        
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(recentFile);
 
            javaJre.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
                    	                    	
                        Starter.Load(lbs, "jre");
                        
                        
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(javaJre);
            
            recentFiles.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
                        
                    	
                    	LoadFile("/home/student");
                        
                        
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(recentFiles);
            
            
            
            
            gui.setPreferredSize(new Dimension(800, 500));
            
            if(OSValidator.isUnix()){
            	
            	String file = new File(".").getAbsoluteFile().getPath();
            	
            	LoadFiles(file);
            	
            }

            
            
        }
        return gui;
    }

    static class Starter implements Runnable {

    	JTree tree = null;
    	
    	String file;
    	
    	public Starter(JTree tree){
    		
    		this.tree = tree;
    	}
    	
        public void run() {
            
         	 DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        	 
        	 DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        	 
        	 root.removeAllChildren();
        	
        	 File g = new File(file);
        	 
        	 
        	 if(g.isDirectory() == true){
        	 
        	 File []files = FileBrowser.GetFiles("jre");
        	
        	 for(File file: files)
        	
        		 JarReader.getClassesFromJar(file.getPath(), tree);
        	
        	 }
        	 else {
        		 
        		 try{
        		 JarZip.Uncompress(file, "dest", tree);
        		 }
        		 catch(Exception e){};
        		 
        	 }
        	 
        }

        public static void Load(JTree tree, String file) {
        	
        	Starter starter = new Starter(tree); 
        	
        	starter.file = file;
        	
            new Thread(starter).start();
        }

    }
   
    ConcreteFlyweight flyweight;
    
    
    public void showRootFile() {
        // ensure the main files are displayed
        tree.setSelectionInterval(0,0);
        
        
        setColumnWidth(0,-1);

    }

    private TreePath findTreePath(File find) {
        for (int ii=0; ii<tree.getRowCount(); ii++) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)object;
            File nodeFile = (File)node.getUserObject();

            String n0 = nodeFile.getAbsolutePath();
            
            String n1 = find.getAbsolutePath();
            
            if (n0 == n1) {
                return treePath;
            }
        }
        // not found!
        return null;
    }

    private TreeNode findTreeNode(File find) {
        for (int ii=0; ii<tree.getRowCount(); ii++) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)object;
            File nodeFile = (File)node.getUserObject();

            String n0 = nodeFile.getAbsolutePath();
            
            String f0 = find.getAbsolutePath();
            
            if (n0.trim().equals(f0.trim()) == true) {
                return node;
            }
        }
        // not found!
        return null;
    }

    
    private void showErrorMessage(String errorMessage, String errorTitle) {
        JOptionPane.showMessageDialog(
            gui,
            errorMessage,
            errorTitle,
            JOptionPane.ERROR_MESSAGE
            );
    }

    private void showThrowable(Throwable t) {
        t.printStackTrace();
        JOptionPane.showMessageDialog(
            gui,
            t.toString(),
            t.getMessage(),
            JOptionPane.ERROR_MESSAGE
            );
        gui.repaint();
    }

    /** Update the table on the EDT */
    private void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (fileTableModel==null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                table.setGridColor(Color.white);
                
                    Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight( icon.getIconHeight()+rowIconPadding );

                    setColumnWidth(0,-1);
                    cellSizesSet = true;
                    
                
                    table.repaint();
                    
                
            }
        });
    }
    
    private void setColumnWidth(int column, int width) {
    	if(table.getColumnCount() <= column)return;
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (width<0) {
            // use the preferred width of the header..
            JLabel label = new JLabel( (String)tableColumn.getHeaderValue() );
            Dimension preferred = label.getPreferredSize();
            // altered 10->14 as per camickr comment.
            width = (int)preferred.getWidth()+14;
        }
        //tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
    }

    public void LoadNodes(DefaultMutableTreeNode node){
    	
        
            File file = (File) node.getUserObject();
            if (file.isDirectory()) {
                File[] files = fileSystemView.getFiles(file, true); //!!
                if (node.isLeaf()) {
                    for (File child : files) {
                        if (child.isDirectory()) {
                        	node.add(new DefaultMutableTreeNode(child));
                        }
                    }
                }
                setTableData(files);
            }
            return;
   	
    }

    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    setTableData(files);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    /** Update the File details view with the details of this File. */
    private void setFileDetails(File file) {
        currentFile = file;
        Icon icon = fileSystemView.getSystemIcon(file);
        fileName.setIcon(icon);
        fileName.setText(fileSystemView.getSystemDisplayName(file));
        path.setText(file.getPath());
        isFile.setSelected(file.isFile());

        JFrame f = (JFrame)gui.getTopLevelAncestor();
        if (f!=null) {
            f.setTitle(
                APP_TITLE +
                " :: " +
                fileSystemView.getSystemDisplayName(file) );
        }

        gui.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception weTried) {
                }
                JFrame f = new JFrame(APP_TITLE);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                FileBrowser FileBrowser = new FileBrowser();
                f.setContentPane(FileBrowser.getGui(f));

                try {
                    URL urlBig = FileBrowser.getClass().getResource("fb-icon-32x32.png");
                    URL urlSmall = FileBrowser.getClass().getResource("fb-icon-16x16.png");
                    ArrayList<Image> images = new ArrayList<Image>();
                    images.add( ImageIO.read(urlBig) );
                    images.add( ImageIO.read(urlSmall) );
                    f.setIconImages(images);
                    
                } catch(Exception weTried) {}

                //f.setPreferredSize(new Dimension(500, 300));
                f.pack();
                f.setLocationByPlatform(true);
                f.setMinimumSize(f.getSize());
                
                f.setVisible(true);

                FileBrowser.showRootFile();
                
            }
        });
    }
    
    static public FileBrowser browser;
    
    
    
    
}

/** A TableModel to hold File[]. */
class FileTableModel extends AbstractTableModel {

    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
        "Icon",
        "File",
        "Path/name",
        "Size",
        "Last Modified",
        "R",
        "W",
        "E",
        "D",
        "F",
    };

    FileTableModel() {
        this(new File[0]);
    }

    FileTableModel(File[] files) {
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
    	
    	if(column > 1)
    		return null;
    	
        File file = files[row];
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
                return file.length();
            case 4:
                return file.lastModified();
            case 5:
                return file.canRead();
            case 6:
                return file.canWrite();
            case 7:
                return file.canExecute();
            case 8:
                return file.isDirectory();
            case 9:
                return file.isFile();
            default:
                System.err.println("Logic Error");
        }
        return "";
    }

    public int getColumnCount() {
        return 2;//columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}

/** A TreeCellRenderer for a File. */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    private FileSystemView fileSystemView;

    private JLabel label;

    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}


class Events implements Eventer {
	
	public SyntaxHighlighter sh;
	
	public List<ArrayList> ll;
	
	public List<Integer> ws;
	
	
	public void action(){
		
		
				
		if(sh == null)
			return;
		
		
		if(ll == null)
			return;
		
		
		int px = sh.highlighter.colNum;
		
		int i = sh.highlighter.rowNum ;
		
		ArrayList<Lexeme> L = ll.get(i);
		
		px = px - ws.get(i);
		
		int ws = 0;
		
		Boolean first = false;
		
		for(Lexeme e: L){
					
			if(e.getLexemeKind() == LexemeKind.WHITESPACE && first == false)
				ws = e.end().column;
			else first = true;
			
			if(e.begin().column - ws <= (px) && e.end().column - ws >= (px)) {
				
				System.out.println("\nToken found " + e.image() + " of type " + e.getLexemeKind().name());
				
								
				Lexeme left = e.previous();
				
				Lexeme right = e.next();
		
			    System.out.println("Left <- " + left.image());
			    
			    System.out.println("Right -> " + right.image());
				
				break;
			}
		}
		
		
	}
	
	
}

abstract class Strategy {
	
	public List<ArrayList> ll;
	
	abstract public void Parse(String file, Boolean isContent) throws Exception;
	
	public static Strategy StrategFactory(JTree trees){
		
		JavaStrategy strategy = new JavaStrategy(trees);
		
		return strategy;
		
	}
	
}

class JavaStrategy extends Strategy {
	
	public JTree tree;
	
	public JavaStrategy(JTree tree){
		
		this.tree = tree;
		
	}
	
	public int GetLineNumber(ArrayList l){
		
		if(l == null || l.size() == 0)
			return 0;
		
		Lexeme lexeme = (Lexeme)l.get(l.size() - 1);
		
		return lexeme.begin().line;
	}
	
	
	public List<ArrayList> toList(ArrayList l){
		
		
		int N = GetLineNumber(l);
		
		
		
		
		List<ArrayList> ls = new LinkedList();
		
		for(int i = 0; i < N;i++)
			ls.add(new ArrayList());
		
		
		int i = 0;
		
		while(i < l.size()){
			
			
			Lexeme lexeme = (Lexeme)l.get(i);
			
			int c  = lexeme.begin().line;
		
			ArrayList p = ls.get(c - 1);
			
			p.add(lexeme);
			
			
			i++;
		}
		
		return ls;
		
	}
	
	//public List<ArrayList> ll = null;
	
	public void Parse(String file, Boolean isContent) throws Exception{

  	
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("file");
    	
    	DefaultTreeModel model = new DefaultTreeModel(root);
    	
    	tree.setModel(model);
  
    	DefaultMutableTreeNode nodes = new DefaultMutableTreeNode("tokens");
    	
		
		ArrayList<Lexeme> L = TreeExample.Parse(tree, file, isContent);
		
		root.add(nodes);
		
		for(Lexeme e: L){
	
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(e.image());
			
			ast a = new ast();
			a.text = e.image();
			a.start = new Point( e.begin().column, e.begin().line);
			a.end = new Point( e.end().column, e.end().line);
			
			node.setUserObject(a);
		
			nodes.add(node);
		
			
		}
		
		
		model.reload();
		
		tree.setModel(model);
		
		tree.repaint();
		
		ll = toList(L);
		
	}
	
}

class JarZip {
	
	public static void Uncompress(String source, String dest) throws IOException {
	
		File d = new File(dest);

		String destFile = d.getAbsolutePath();
		
		System.out.println(destFile);
		
		if(d.exists() == false)
			d.mkdir();
		
	java.util.jar.JarFile jar = new java.util.jar.JarFile(source);
	java.util.Enumeration enumEntries = jar.entries();
	while (enumEntries.hasMoreElements()) {
	    java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
	    java.io.File f = new java.io.File(destFile + java.io.File.separator + file.getName());
	    if (file.isDirectory()) { // if its a directory, create it
	        f.mkdir();
	        continue;
	    }
	    java.io.InputStream is = jar.getInputStream(file); // get the input stream
	    java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
	    while (is.available() > 0) {  // write contents of 'is' to 'fos'
	        fos.write(is.read());
	    }
	    fos.close();
	    is.close();
	}
	
	}
	
	public static void Uncompress(String source, String dest, JTree tree) throws IOException {
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("file");
    	
    	DefaultTreeModel model = new DefaultTreeModel(root);
    	
    	tree.setModel(model);
  
    	DefaultMutableTreeNode nodes = new DefaultMutableTreeNode("classes");
		
		root.add(nodes);
    	
    	
		File d = new File(dest);

		String destFile = d.getAbsolutePath();
		
		System.out.println(destFile);
		
		if(d.exists() == false)
			d.mkdir();
		
	java.util.jar.JarFile jar = new java.util.jar.JarFile(source);
	
	java.util.Enumeration enumEntries = jar.entries();
	
	
	
	int i = 0;
	
	while (enumEntries.hasMoreElements()) {
	   
		
		
		java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
	    java.io.File f = new java.io.File(destFile + java.io.File.separator + file.getName());
	    
	    
	    
	    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
	    
	    node.setUserObject(file);
	    
	    if (file.isDirectory()) { // if its a directory, create it
	        f.mkdir();
	        nodes.add(node);
	        
	        //tree.expandRow(i++);
	        
	    model.reload();
	        continue;
	    }
	    java.io.InputStream is = jar.getInputStream(file); // get the input stream
	    java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
	    while (is.available() > 0) {  // write contents of 'is' to 'fos'
	        fos.write(is.read());
	    }
	    fos.close();
	    is.close();
	    
	    nodes.add(node);
	    
	    
	    model.reload();
	    
	    //tree.expandRow(i++);
	   
	}
	
	model.reload();
	
	tree.updateUI();
	
	}
}

interface Flyweight {
        
    public void Display(String file,  JPanel panel, JTree tree, JTree jartree, String content) throws Exception;
    
}

class ConcreteFlyweight implements Flyweight {
	   
    public SyntaxHighlighter highlighter = null;

       	public ConcreteFlyweight(String exts){
    	
    		highlighter = FlyweightFactory.GetHighlighter(exts);
    	
    }
      	
      	

       	public String LoadJar(String file){
       	
       		
       		
       		StringWriter sw = new StringWriter();
       		
       		PrintWriter writer = new PrintWriter(sw);
       		
       	try {
       	    com.strobel.decompiler.Decompiler.decompile(
       	    file,
       	        new com.strobel.decompiler.PlainTextOutput(writer)
       	    );
       	}
       	finally {
       	    writer.flush();
       	}
       	
       	return sw.toString();
       	
   	}   
       	
       	static public String loadClass(String fileName) {
    		try {
    			ClassParser classParser = new ClassParser(fileName);
    			JavaClass classFile = classParser.parse();
    			
    			String cc = classFile.toString(); 
    			
    			Field [] f = classFile.getFields();
    			
    			if(f != null)
    			for(Field b: f)
        			
        			cc = cc + "\\" + b.toString().toString();
    			
    			
    			Method []m = classFile.getMethods(); 
    			
    			if(m != null)
    			for(Method b: m)
    			
    			cc = cc + "\\" + b.getCode().toString();
    			
    			return cc;
    			
    		} catch (IOException e) {
    			System.out.println("Could not find FileName file. Exiting");
    			return "";
    		}
    	}
       	
       	
    public void Display(String file, JPanel panel, JTree trees, JTree jartree, String content) throws Exception{
 
    	
    	if(file.endsWith(".jar")) {
    	
    	
    		FileBrowser.Starter.Load(jartree, file);
    	
    		return;
    	 
    	}
    	 
    	
    	Strategy strategy = Strategy.StrategFactory(trees);
    	
    	Boolean isContent = true;

    	String entry = content;
    	
    	
    	
    	if(content == "")
    	{
    		isContent = false;
    
    	FileInputStream fisTargetFile;
		try {
			fisTargetFile = new FileInputStream(new File(file));
			content = IOUtils.toString(fisTargetFile);
		} catch (Exception e) {}
        
		
		entry = new File(file).getAbsolutePath();
		
    	}
    	
       	strategy.Parse(entry, isContent);

    		
    
		
		//content = content.trim();

    	
		
		panel.removeAll();
       	
       	highlighter.setHighlightOnMouseOver(false);
       	
       	highlighter.scrollRectToVisible(new Rectangle(0,0,1000,1000));
       	
       	highlighter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       	highlighter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       			
       	highlighter.setGutterVisible(false);
       
    	panel.add(highlighter);
    	panel.setVisible(true);
    	panel.validate();
    	panel.repaint();
    	
    	highlighter.setContent(content);
    	
    	highlighter.highlighter.setCaretColor(Color.BLACK);
    	
    	Events events = new Events();
    	
    	
    	if(isContent == false)
    	
    	events.ws = highlighter.highlighter.Whitespaces(file);
    	
    	else
    		
    		events.ws = highlighter.highlighter.WhitespacesContent(content);
    	
    	
    	events.sh = highlighter;
    	
    	events.ll = strategy.ll;
    	
    	highlighter.highlighter.eventer = events;
    	
    	
    	
    	panel.repaint();
 
    }
    
}

class FlyweightFactory {
    
	private Map<String, ConcreteFlyweight> flyweights = new HashMap<String, ConcreteFlyweight>();

    public ConcreteFlyweight get(String key) {
        ConcreteFlyweight flyweight = flyweights.get(key);

        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(key);
            flyweights.put(key, flyweight);
        }

        return flyweight;
    }
      	
    static public SyntaxHighlighter GetHighlighter(String exts){
    	
    	
    	SyntaxHighlighterParser parser = new SyntaxHighlighterParser(new BrushJava());
    	
    	switch (exts) {
        
    	case ".java":  
    		parser = new SyntaxHighlighterParser(new BrushJava());
    		break;
    		
        case ".cs":  
        	parser = new SyntaxHighlighterParser(new BrushCSharp());
            break;
        	
        default:
        	
        	;
                 
    	}

      	SyntaxHighlighter highlighter = new SyntaxHighlighter(parser, new ThemeEclipse());
   	
    	return highlighter;	
 	
    }

   }

