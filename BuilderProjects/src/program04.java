
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MenuBar;
import java.awt.event.*;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import nativeswing.common.Utils;
import nativeswing.NativeInterface;
import nativeswing.components.JWebBrowser;

class Database extends AbstractTableModel {
    private List<TableHeader> headers;
    private List<List<TableData>> data;
    
    public Database() {
        headers = new ArrayList<TableHeader>();
        data = new ArrayList<List<TableData>>();
    }
    public List<TableData> addRow() {
        List<TableData> row = new ArrayList<TableData>();
        for(TableHeader col:headers)
            row.add(col.CreateTableData()); // wywo³anie metody fabrykuj¹cej
        data.add(row);
        fireTableStructureChanged();
        return row;
    }
    public void addCol(TableHeader type) {
        headers.add(type);
        for(List<TableData> row:data) {
        	
            row.add(type.CreateTableData()); // wywo³anie metody fabrykuj¹cej
        }
        fireTableStructureChanged();
    }

    public void ClearDatabase(){
    	headers.clear();
    	data.clear();
    	
    	fireTableDataChanged();
    	fireTableStructureChanged();
    	
    }
    
    public void ClearData(){
    	data.clear();
    	fireTableDataChanged();
    	fireTableStructureChanged();
    }
    
    public int getRowCount() { return data.size(); }
    
    public int getColumnCount() { return headers.size(); }
    
    public String getColumnName(int column) {
        return headers.get(column).toString();
    }
    
    public Object getValueAt(int row, int column) {
        if(row >= 0 && column >= 0)
    	return data.get(row).get(column);
        	else return null;
    }
    public void setValueAt(Object object, int row, int column) {
        
    	List<TableData> td = data.get(row);
    	
    	TableData dd = td.get(column);
    	
    	dd.set(object);
    	
    	    	
    	fireTableCellUpdated(row, column);
    	
    	fireTableDataChanged();
    	
    }

    public void setValueAt(TableData t, int row, int column) {
        
    	List<TableData> td = data.get(row);
    	
    	td.set(column, (TableData) t);
    	    	
    	fireTableCellUpdated(row, column);
    	
    	fireTableDataChanged();
    }

    
    public List<TableData> GetRow(int row){
    	return data.get(row);
    }
    
    public void SetRow(int row, List<TableData> d){
    	data.set(row, d);
    	fireTableDataChanged();
    }
    
    public static List<TableData> CreateCopy(List<TableData> d){
    	
    	List<TableData> c = new ArrayList<TableData>();
    	
    	for(TableData dd: d){
    		
    		TableData cloned = dd.CreateTableData();
    		
    		c.add(cloned);
    		
    	}
    	return c;
    	
    }
    
    public boolean isEditable = false;
    
    public boolean isCellEditable(int row, int col) { 
        return isEditable; 
    }
}

interface TableData {

	final static Random rnd = new Random();
    TableData CreateTableData();
    void set(Object object);
}

class TableDataInt extends Object implements TableData, Cloneable
{

    private int data;
    
    public TableDataInt() { 
    	
    	data = (int)(Math.random()* 100.0); 
    	
    }
    
   public TableDataInt(int i) { 
    	
    	data = i; 
    	
    }

   public void set(Object object){
	   data = (int)object;
   }
    
    
    public String toString() { return Integer.toString(data); }
    
	public Object clone(){
		
		Object kopia = null;
		try {
			kopia = super.clone();
		}
		catch(CloneNotSupportedException e){};
		
		return kopia;
	}
    
    public TableData CreateTableData(){
    	
    	TableData tableData =  null;
    	
    	
    	tableData = (TableData) this.clone();
    	
    	return tableData;
    }
    
}

class TableDataDouble extends Object implements TableData, Cloneable
{
	
    private double data;
    
    public TableDataDouble() { 
    	
    	data = (double)(Math.random()* 100.0); 
    	
    	
    }
    
    public void set(Object object){
 	   data = (double)object;
    }
    
    public String toString() { return Double.toString(data); }
    
	public Object clone(){
		
		Object kopia = null;
		try {
			kopia = super.clone();
		}
		catch(CloneNotSupportedException e){};
		
		return kopia;
	}
    
    public TableData CreateTableData(){
    	
    	TableData tableData =  null;
    	
    	
    	tableData = (TableData) this.clone();
    	
    	return tableData;
    }
    
}
class TableDataChar extends Object implements TableData, Cloneable
{
	
    private char data;
    public TableDataChar() { 
    	
    	data = (char)(Math.random()* 100.0); 
    	
    	
    }
    
    public void set(Object object){
 	   data = (char)object;
    }
    
    public String toString() { return Integer.toString(data); }
    
	public Object clone(){
		
		Object kopia = null;
		try {
			kopia = super.clone();
		}
		catch(CloneNotSupportedException e){};
		
		return kopia;
	}
    
    public TableData CreateTableData(){
    	
    	TableData tableData =  null;
    	
    	
    	tableData = (TableData) this.clone();
    	
    	return tableData;
    }
    
}

class TableDataStr extends Object implements TableData, Cloneable
{
	
    private String data;
    public TableDataStr() { 
    	
    	data = Double.toHexString((Math.random()* 100.0)); 
    	
    	
    }
    
  public TableDataStr(String s) { 
    	
    	data = s; 
    	
    	
    }
    
  public void set(Object object){
	   data = (String)object;
  }
  
    public String toString() { return data; }
    
	public Object clone(){
		
		Object kopia = null;
		try {
			kopia = super.clone();
		}
		catch(CloneNotSupportedException e){};
		
		return kopia;
	}
    
    public TableData CreateTableData(){
    	
    	TableData tableData =  null;
    	
    	
    	tableData = (TableData) this.clone();
    	
    	return tableData;
    }
    
}

/* ... */

class TableHeader
{
    private String type;
    
    private String name;
    
    
public static TableData GetPrototype(String name){
	
	if(name == "Integer")
		return new TableDataInt();
	else if(name == "Double")
			return new TableDataDouble();
	else if(name == "char")
		return new TableDataChar();
	else if(name == "String")
		return new TableDataStr();
	
	return new TableDataInt();
}

    public TableHeader(String type) {
        
    	this.type = type;
    	
    	tableData = GetPrototype(type);

}
    
   public TableHeader(String type, String name) {
	   
    	this.type = type;
    	
    	this.name = name;
    	
    	tableData = GetPrototype(type);

}
    
    public String toString() {
    	
    	if(name != null)
    	if(name != "")
    		return name;
    	
    	return type; 

    }
    public Object object = null;
    public TableData tableData = null;
    
    public TableData CreateTableData(){
    	
    	return tableData.CreateTableData();
    }
}

class AdvancedRenderer implements TableCellRenderer
{
	
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public static Font font = new Font("Consolas", Font.BOLD, 25);
	
	  public AdvancedRenderer() {
		    super();
		    }
	
		  
	public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
		        table, value, isSelected, hasFocus, row, column);
		    ((JLabel) renderer).setOpaque(true);
		    Color foreground, background;
		    foreground = Color.black;
		    background = Color.white;
		    
//		    if(column == 3){
//		    	
//		    	renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
//		    	
//		    }
		    if (isSelected) {
		      foreground = Color.black;
		      background = Color.green;
		    } else {
		      if (row % 2 == 0) {
		        foreground = Color.blue;
		        background = Color.white;
		      } else {
		        foreground = Color.black;
		        background = new Color(250,250,250);
		      }
		    }
		    renderer.setForeground(foreground);
		    renderer.setBackground(background);
		    return renderer;
		  }
	
    public void setValue(Object value)
    {
 
    }
}

class DatabaseProxy {
	
	
public List<Info> original;	
	
	
public List<Info> copy;


public List<Info> filtered;

public DatabaseProxy GetInstance(){
	
	
	return new DatabaseProxy();
	
}

public void update_filtered(List<TableData> dd, int row){
	
	if(filtered == null)
		return;
	Info info = filtered.get(row);
	
	TableData d0 = dd.get(1);
	info.cat = d0.toString();
	
	TableData d1 = dd.get(3);
	info.title = d1.toString();
	
	TableData d2 = dd.get(2);
	info.Id = d2.toString();
	
}

}


public class program04 extends JPanel {
    
	
static public DatabaseProxy proxy = new DatabaseProxy();	
	

public DatabaseProxy GetProxy(){
	
	
	if(proxy == null)
		proxy = new DatabaseProxy();
	
	return proxy;
	
	
	
}


static public void UpdateDatabase(){
	
	if(proxy == null)
		return;
	
	SQLiteSearch.UpdateSearch(proxy.copy);
	
	
}



static public void CreateNewProxy(){
	proxy = new DatabaseProxy();
}


    final static TableHeader []th = new TableHeader[] {
            new TableHeader("Integer"),
            new TableHeader("Double"),
            new TableHeader("char"),
            new TableHeader("String"),
        };
	
    final static String []types = new String[] { "Integer", "Double", "char", "String"}; 
    
    public static TableHeader GetTableHeader(String type){
    	
    	for(TableHeader ths : th){
    		
    		if(ths.toString() == type)
    			return ths;
    		
    	}
    	
    	return th[0];
    }
    
    public static void CreateSQLiteTemplate(){
    	
    	if(database == null)
    		return;
    	
    	database.ClearDatabase();
    
    	TableHeader H = new TableHeader("Integer", "Nr");
    	TableHeader HC = new TableHeader("String", "Cat");
    	TableHeader H0 = new TableHeader("String", "Title");
    	TableHeader H1 = new TableHeader("String", "Id");
    	TableHeader H2 = new TableHeader("String", "Imgs");
   	
    	database.addCol(H);
    	database.addCol(HC);
    	database.addCol(H0);
    	database.addCol(H1);
    	database.addCol(H2);
    	
    	
    	catalogs.ClearDatabase();
    	
    	catalogs.addCol(H);
    	catalogs.addCol(HC);
    	

    	files.ClearDatabase();
    	
    	files.addCol(H);
    	files.addCol(HC);
    	files.addCol(H0);
    	files.addCol(H1);
    	files.addCol(H2);

    	
    	
    }
    
    public program04() {
    	
     
        
      }


    public static List<TableData> newdatas = null;
    
    public static void LoadNewData(){
    	
    	if(editor == null)
    		return;
    	
    	int N = editor.getRowCount();
    	int i = 0;
    	while(i < N){
    		
    		List<TableData> row = editor.GetRow(i);
    		
    	    TableData td = row.get(1);
    		
    	    newdatas.set(i, td);
    	    
    	       	    
    		i++;
    	}
    	
    	
    }
    
    public static void ReloadCategories(){
    	
    	
        List<String> cats = SQLiteSearch.sql.GetCategories(proxy.copy);
        
        catalogs.ClearData();
        
        int i = 0;
        for(String ns : cats){
        	
        
        	
        	catalogs.addRow();
        	
        	int row = catalogs.getRowCount();
        	
        	TableData d = new TableDataInt(i);
        	TableData h0 = new TableDataStr(ns);
        	               	
        	catalogs.setValueAt(d,row -1, 0);
        	catalogs.setValueAt(h0, row -1, 1);
        				                	
        	i++;
        	
        	
        }
    	
    	
    	
    }
    
    
    public static void LoadEditor(JFrame p, List<TableData> newdata){
    	
    	
    	JFrame master = p;
    	
    	JPanel panel = new JPanel();
    	
    	panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS) );
    	
        editor = new Database();
        
        
        editor.ClearDatabase();
    	
    	TableHeader H = new TableHeader("Integer", "Nr");
    	TableHeader HC = new TableHeader("String", "Cat");
        
        editor.addCol(H);
        editor.addCol(HC);
    
        
        int i = 0;
        for(TableData d: newdata){
        	
        	
        	editor.addRow();
        	
        	int row = editor.getRowCount();
        	
        	TableData d0 = new TableDataInt(i);
        	TableData h0 = new TableDataStr(d.toString());
    

        	editor.setValueAt(d0, row -1, 0);
        	editor.setValueAt(h0, row -1, 1);
    
        	
        	i++;
        	
        	
        }
        
        newdatas = newdata;
        
        editor.isEditable = true;
        
        JTable table = new JTable(editor);
        //table.setDefaultRenderer(Object.class, new AdvancedRenderer());		        
        JMenuBar bar = new JMenuBar();
        
        JButton row = new JButton("Dodaj Wiersz");
        JButton col = new JButton("Dodaj Kolumnê");
        JButton pms = new JButton("Settings");
               
        
        bar.add(row);
        bar.add(col);
        bar.add(pms);
        
        
        panel.add(bar);
        
       
       table.setMinimumSize(new Dimension(10000,10000));
       
       panel.add(new JScrollPane(table), BorderLayout.CENTER);
   
     	JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setLocationByPlatform(true);
        frame.setModal(true);
       
        frame.setAlwaysOnTop(true);
        frame.setVisible(true); 
       
        frame.addWindowListener(new WindowAdapter() 
        {
          public void windowClosed(WindowEvent e)
          {
            System.out.println("jdialog window closed event received");
           // LoadNewData();
          }
         
          public void windowClosing(WindowEvent e)
          {
            System.out.println("jdialog window closing event received");
          }
        });
       
       
   
    	
    	
    	
    	
    }

    
    
    
    static SimpleWebBrowser wb = null;
    
    static JWebBrowser jwb = null;
    
    static Database database = null;
    
    static Database catalogs = null;
    
    static Database files = null;
    
    static Database editor = null;
    
	public static void main(String[] args) {
		
		
		 nativeswing.common.UIUtils.setPreferredLookAndFeel();
		    NativeInterface.open();
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {

		    	  final JPanel panel = new JPanel();
			       
		    	  panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS) );
		    	  
			        database = new Database();
			        
			        JTable table = new JTable(database);
			        table.setDefaultRenderer(Object.class, new AdvancedRenderer());		        
			        JMenuBar bar = new JMenuBar();
			        
			        JButton row = new JButton("Dodaj Wiersz");
			        JButton col = new JButton("Dodaj Kolumnê");
			        JButton pms = new JButton("Settings");
			        JButton sql = new JButton("Import SQLite");
			        JButton src = new JButton("New SQLite Search");
			        
			        
			        bar.add(row);
			        bar.add(col);
			        bar.add(pms);
			        bar.add(sql);
			        bar.add(src);
			        
			        panel.add(bar);
			        
			       
			       table.setMinimumSize(new Dimension(10000,10000));
			       
			       panel.add(new JScrollPane(table), BorderLayout.CENTER);

			        JPanel panelup = new JPanel();
			        panelup.setLayout( new BoxLayout(panelup, BoxLayout.Y_AXIS) );
			        catalogs = new Database();
			        JTable cats = new JTable(catalogs);
			        cats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			        cats.setMinimumSize(new Dimension(10000,10000));
			        
			        
			        JMenuBar barup = new JMenuBar();
			        barup.setLayout(new FlowLayout(FlowLayout.LEFT));
			        
			        JButton synup = new JButton("<- Synch");
			        JButton syndw = new JButton("Synch ->");
			        
			        
			        barup.add(synup, BorderLayout.EAST);
			        barup.add(syndw, BorderLayout.EAST);
			        			        
			        panelup.add(barup, BorderLayout.CENTER);
	
			        panelup.add(new JScrollPane(cats), BorderLayout.CENTER);
			        
			        
			        
			        JPanel paneldw = new JPanel();
			        paneldw.setLayout( new BoxLayout(paneldw, BoxLayout.Y_AXIS) );
			        paneldw.setVisible(true);
			        files = new Database();
			        JTable fls = new JTable(files);
			        fls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			        fls.setMinimumSize(new Dimension(10000,10000));
			        paneldw.add(new JScrollPane(fls), BorderLayout.CENTER);
			        
			        
			     	  JSplitPane splitPaner = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	                             panelup, paneldw);
			    	  	splitPaner.setOneTouchExpandable(true);
			    	  	splitPaner.setDividerLocation(250);
			        
			        
			       wb = new SimpleWebBrowser(); 
		
			       
			    	  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	                             wb, splitPaner);
			    	  	splitPane.setOneTouchExpandable(true);
			    	  	
			    	  	splitPane.setDividerLocation(0.3);
			       
			       
			    	  JSplitPane splitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	                              panel, splitPane);
			    	  splitPanes.setOneTouchExpandable(true);
			    	  splitPanes.setDividerLocation(250);

			    	  
			        row.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			                database.addRow();
			            }
			        });
			        
			        pms.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			                	
			                	
			            	SQLiteSearch sql = SQLiteSearch.GetInstance();
			            	sql.Settings();
			                
			        
			            }
			        });
			        
			        
			        sql.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			            				            	
			            	CreateSQLiteTemplate();
			            	
			            	CreateNewProxy();
			            	
			            	table.updateUI();
			            	
			            	SQLiteSearch sql = SQLiteSearch.GetInstance();
			            	
			                List<Info> infos = sql.GetInfos();
			                
			                int i = 0;
			                for(Info ns : infos){
			                	
			                			                	
			                	database.addRow();
			                	
			                	int row = database.getRowCount();
			                	
			                	TableData d = new TableDataInt(i);
			                	TableData h0 = new TableDataStr(ns.cat);
			                	TableData d0 = new TableDataStr(ns.Id);
			                	TableData d1 = new TableDataStr(ns.title);
			                	TableData d2 = new TableDataStr(ns.imgs);
               	
			                	database.setValueAt(d,row -1, 0);
			                	database.setValueAt(h0, row -1, 1);
			                	database.setValueAt(d0, row - 1, 2);
			                	database.setValueAt(d1, row - 1, 3);
			                	database.setValueAt(d2, row - 1, 4);
			                	
			                	i++;
			                	
			                	
			                }
			        
			                table.updateUI();
			                
			                proxy.original = infos;
			                
			                List<String> cats = sql.GetCategories();
			                
			                i = 0;
			                for(String ns : cats){
			                	
			                
			                	
			                	catalogs.addRow();
			                	
			                	int row = catalogs.getRowCount();
			                	
			                	TableData d = new TableDataInt(i);
			                	TableData h0 = new TableDataStr(ns);
			                	               	
			                	catalogs.setValueAt(d,row -1, 0);
			                	catalogs.setValueAt(h0, row -1, 1);
			                				                	
			                	i++;
			                	
			                	
			                }
			                
			                proxy.copy = new ArrayList(infos);
			                
			            }
			        });
			        
			        
			        col.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			                Object option = JOptionPane.showInputDialog(
			                    panel,
			                    "Podaj typ kolumny",
			                    "Dodaj Kolumnê",
			                    JOptionPane.QUESTION_MESSAGE,
			                    null,
			                    types, null);
			                if(option == null)
			                    return;
			                
			                TableHeader H = GetTableHeader((String) option);
			                
			                
			                database.addCol((TableHeader)H);
			            }
			        });

			        src.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			            	
			            	table.updateUI();
			            	
			            	SQLiteSearch sql = SQLiteSearch.GetInstance();
			            	
			                List<Info> infos = sql.NewSearch();
			                
			                
			                for(Info ns : infos){
			                	
			                	
			                	database.addRow();
			                	
			                	int row = database.getRowCount();
			                	
			                	TableData d = new TableDataInt(row - 1);
			                	TableData h0 = new TableDataStr(ns.cat);
			                	TableData d0 = new TableDataStr(ns.Id);
			                	TableData d1 = new TableDataStr(ns.title);
			                	TableData d2 = new TableDataStr(ns.imgs);
               	
			                	database.setValueAt(d, row -1, 0);
			                	database.setValueAt(h0, row -1, 1);
			                	database.setValueAt(d0, row - 1, 2);
			                	database.setValueAt(d1, row - 1, 3);
			                	database.setValueAt(d2, row - 1, 4);
			                	
			                	
			                }
			        
			                table.updateUI();
			                
			            }
			        });
			        
	        		    	  
   		          table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			            public void valueChanged(ListSelectionEvent lse) {
			                if (!lse.getValueIsAdjusting()) {
			                    System.out.println("Selection Changed");
			                    
			                    
			                    int i = table.getSelectedRow();
			                    
			                    String s = ((TableDataStr)database.getValueAt(i, 3)).toString();
	
			                    
			                    String url =s.trim();
			                    
			                    if(url == "")
			                    return;
			                    
			                    if(url == null)
			                    	return;
			                    
			                    System.out.println(url);
			                    
			                    String urls = "http://www.youtube.com/watch?v=" + url;
		
			                    wb.jwb.navigate(urls);
			                    
			                 			                    
			                }
			            }

			        });
		    	  
   		          
  		        synup.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent ev)
		            {
		             System.out.println("Synchronization has been performed...");
		             
		            	CreateSQLiteTemplate();
		            			            	
		            	table.updateUI();
		            	
		            			            	
		                List<Info> infos = proxy.copy;
		                
		                int i = 0;
		                for(Info ns : infos){
		                	
		                			                	
		                	database.addRow();
		                	
		                	int row = database.getRowCount();
		                	
		                	TableData d = new TableDataInt(i);
		                	TableData h0 = new TableDataStr(ns.cat);
		                	TableData d0 = new TableDataStr(ns.Id);
		                	TableData d1 = new TableDataStr(ns.title);
		                	TableData d2 = new TableDataStr(ns.imgs);
        	
		                	database.setValueAt(d,row -1, 0);
		                	database.setValueAt(h0, row -1, 1);
		                	database.setValueAt(d0, row - 1, 2);
		                	database.setValueAt(d1, row - 1, 3);
		                	database.setValueAt(d2, row - 1, 4);
		                	
		                	i++;
		                	
		                	
		                }
		        
		                table.updateUI();
		                
		                proxy.original = infos;
		                
		                List<String> cats = SQLiteSearch.GetCategories(infos);
		                
		                i = 0;
		                for(String ns : cats){
		                	
		                
		                	
		                	catalogs.addRow();
		                	
		                	int row = catalogs.getRowCount();
		                	
		                	TableData d = new TableDataInt(i);
		                	TableData h0 = new TableDataStr(ns);
		                	               	
		                	catalogs.setValueAt(d,row -1, 0);
		                	catalogs.setValueAt(h0, row -1, 1);
		                				                	
		                	i++;
		                	
		                	
		                }
		                
		                
		            }

		             
		             
		             
		            
		        });
   		          
		          cats.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			            public void valueChanged(ListSelectionEvent lse) {
			                if (!lse.getValueIsAdjusting()) {
			                    System.out.println("Selection Changed");
			                    
			                    
			                    int i = cats.getSelectedRow();
			                    
			                    String s = ((TableDataStr)catalogs.getValueAt(i, 1)).toString();
	
			                    
			                    files.ClearData();
			                   			                    
			                    proxy.filtered = Info.Filter(proxy.copy, s);
			                    
				                for(Info ns : proxy.filtered){
				                	
				                	
				                	files.addRow();
				                	
				                	int row = files.getRowCount();
				                	
				                	TableData d = new TableDataInt(row - 1);
				                	TableData h0 = new TableDataStr(ns.cat);
				                	TableData d0 = new TableDataStr(ns.Id);
				                	TableData d1 = new TableDataStr(ns.title);
				                	TableData d2 = new TableDataStr(ns.imgs);
	               	
				                	files.setValueAt(d, row -1, 0);
				                	files.setValueAt(h0, row -1, 1);
				                	files.setValueAt(d0, row - 1, 2);
				                	files.setValueAt(d1, row - 1, 3);
				                	files.setValueAt(d2, row - 1, 4);
				                	
				                	
				                }
				        
				                fls.updateUI();	                    
			                 			                    
			                }
			            }

			        });  
   		          
  		          fls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			            public void valueChanged(ListSelectionEvent lse) {
			                if (!lse.getValueIsAdjusting()) {
			                    System.out.println("Selection Changed");
			                    
		                    
			                    int i = fls.getSelectedRow();
			                    
			                    if(i < 0)
			                    	return;
			                    
			                    String s = ((TableDataStr)fls.getValueAt(i, 3)).toString();
	
			                    
			                    String url =s.trim();
			                    
			                    if(url == "")
			                    return;
			                    
			                    if(url == null)
			                    	return;
			                    
			                    System.out.println(url);
			                    
			                    String urls = "http://www.youtube.com/watch?v=" + url;
		
			                    wb.jwb.navigate(urls);
			                    
			                 			                    
			                }
			            }

			        });  
   		          
  		          
  		        MenuBar  menu = new MenuBar(); 
  		          
  		        final JFrame frame = new JFrame("Design Patterns");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        JMenuBar menuBar = new JMenuBar();
		         

		        frame.setJMenuBar(menuBar);
		         
		        JMenu fileMenu = new JMenu("File");
		        JMenu editMenu = new JMenu("Edit");
		        menuBar.add(fileMenu);
		        menuBar.add(editMenu);
		         
		       
		        JMenuItem newAction = new JMenuItem("New");
		        JMenuItem openAction = new JMenuItem("Open");
		        JMenuItem exitAction = new JMenuItem("Exit");
		        JMenuItem cutAction = new JMenuItem("Cut");
		        JMenuItem copyAction = new JMenuItem("Copy");
		        JMenuItem pasteAction = new JMenuItem("Paste");
		         
		      
		        JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");

		        JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem(
		                "Radio Button1");
		        JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem(
		                "Radio Button2");

		        ButtonGroup bg = new ButtonGroup();
		        bg.add(radioAction1);
		        bg.add(radioAction2);
		        fileMenu.add(newAction);
		        fileMenu.add(openAction);
		        fileMenu.add(checkAction);
		        fileMenu.addSeparator();
		        fileMenu.add(exitAction);
		        editMenu.add(cutAction);
		        editMenu.add(copyAction);
		        editMenu.add(pasteAction);
		        editMenu.addSeparator();
		        editMenu.add(radioAction1);
		        editMenu.add(radioAction2);
		        frame.getContentPane().add(splitPanes);
		        
		    
		        JPanel statusPanel = new JPanel();
		        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		        frame.add(statusPanel, BorderLayout.SOUTH);
		        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 25));
		        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		        JLabel statusLabel = new JLabel("Design patterns - Proxy");
		        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		        statusPanel.add(statusLabel);

		        
		        
		        frame.setSize(800, 600);
		        frame.setLocationByPlatform(true);
		        frame.pack();
		        splitPane.setDividerLocation(0.7);
		        splitPane.setResizeWeight(0.7);
		        frame.setVisible(true);
		        
  		          
  		        fls.addMouseListener(new MouseAdapter() {
  		          public void mouseClicked(MouseEvent e) {
  		             if (e.getClickCount() == 2) {
  		                JTable target = (JTable)fls;
  		                int row = target.getSelectedRow();
  		                int column = target.getSelectedColumn();
  		                
  		              frame.setFocusableWindowState(false);
  		              
  		               		              
  		              List<TableData> data = files.GetRow(row);
  		              
  		              List<TableData> newdata = Database.CreateCopy(data);
  		                
  		              LoadEditor(frame, newdata);
  		              
  		              files.SetRow(row, newdata);
  		              
  		              LoadNewData();
  		              
  		                		              
  		              proxy.update_filtered(newdata, row);
  		              
  		              //ReloadCategories();
  		              
  		              
  		                }
  		          }
  		       });
   		          
		    
		      }
		    });
	        
    }
	
}