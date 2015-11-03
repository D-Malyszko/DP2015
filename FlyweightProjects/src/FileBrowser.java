import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileSystemView;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

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
import java.net.URL;
// stackexchange source code.....
class FileBrowser {

    /** Title of the application */
    public static final String APP_TITLE = "FileBro";
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
    
    public Container getGui(JFrame frame) {
        if (gui==null) {
            gui = new JPanel(new BorderLayout(3,3));
            gui.setBorder(new EmptyBorder(5,5,5,5));

            JMenuBar toolBar = new JMenuBar();
            // mnemonics stop working in a floated toolbar
            //toolBar.setFloatable(false);

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

            JPanel fileView = new JPanel(new BorderLayout(3,3));

            //fileView.add(fileMainDetails,BorderLayout.CENTER);

            detailView.add(fileView, BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(
       
            		JSplitPane.VERTICAL_SPLIT,
                treeScroll,
                detailView);
            
            JPanel simpleOutput = new JPanel(new BorderLayout(3,3));
            progressBar = new JProgressBar();
            simpleOutput.add(progressBar, BorderLayout.EAST);
            progressBar.setVisible(false);

                        
            panel = new JPanel(new BorderLayout());
           
            panel.setVisible(true);
            
            JSplitPane splitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, panel);
                        
            gui.add(splitPanes, BorderLayout.CENTER);
            
            splitPanes.setDividerLocation(0.5);
            
            locateFile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
                        System.out.println("Locate: " + currentFile.getParentFile());
                                              
                        
                        String file = currentFile.getAbsolutePath();
                        
                        String exts = file.substring(file.lastIndexOf("."));
                        
                        ConcreteFlyweight flyweight = factory.get(exts);
                        
                        flyweight.Display(file, panel);
                        
                        
                    } catch(Throwable t) {
                        showThrowable(t);
                    }
                    gui.repaint();
                }
            });
            toolBar.add(locateFile);
         
        }
        return gui;
    }

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

            if (nodeFile==find) {
                return treePath;
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

    /** Add the files that are contained within the directory of this node.
    Thanks to Hovercraft Full Of Eels for the SwingWorker fix. */
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
    
    public static JPanel GetFileBrowser() {
        
    	JPanel f = new JPanel(new BorderLayout());
    	
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception weTried) {
                }

                FileBrowser FileBrowser = new FileBrowser();

                browser = FileBrowser;
                
                try {
                    URL urlBig = FileBrowser.getClass().getResource("fb-icon-32x32.png");
                    URL urlSmall = FileBrowser.getClass().getResource("fb-icon-16x16.png");
                    ArrayList<Image> images = new ArrayList<Image>();
                    images.add( ImageIO.read(urlBig) );
                    images.add( ImageIO.read(urlSmall) );

                } catch(Exception weTried) {}

                f.setMinimumSize(f.getSize());
                f.setVisible(true);

                FileBrowser.showRootFile();
                
    	
    	return f;
    	
    }

    
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

interface Flyweight {
        
    public void Display(String file,  JPanel panel);
    
}

class ConcreteFlyweight implements Flyweight {
    
	   
    public SyntaxHighlighter highlighter = null;

       	public ConcreteFlyweight(String exts){
    	
    		highlighter = FlyweightFactory.GetHighlighter(exts);
    	
    }
     
    public void Display(String file, JPanel panel){
    	
    	String content = "";
    	
    	FileInputStream fisTargetFile;
		try {
			fisTargetFile = new FileInputStream(new File(file));
			content = IOUtils.toString(fisTargetFile, "UTF-8").toString();
		} catch (Exception e) {}
  		
		highlighter.setContent(content);

       	panel.removeAll();
       	highlighter.scrollRectToVisible(new Rectangle(0,0,1000,1000));
       	
       	highlighter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       	highlighter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       			
       	highlighter.setGutterVisible(false);
       
    	panel.add(highlighter);
    	panel.setVisible(true);
    	panel.validate();
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


