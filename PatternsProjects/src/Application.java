
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import org.apache.commons.io.FileUtils;

import nativeswing.NativeInterface;
import nativeswing.components.JWebBrowser;


class Decorators {
	
public JTable table = null;	
	

public void PerformSelect(Database fls, int i){

    String s = ((TableDataStr)fls.getValueAt(i, 3)).toString();
	
    
    String url =s.trim();
    
    if(url == "")
    	return;
    
    if(url == null)
    	return;
    
    System.out.println(url);
    
    String urls = "";
    
    if(url.contains(".js") == true)
    return;
    		
    urls = url;
    

    Application.wb.jwb.navigate(urls);

	
	
}


public void PerformLoad(Application a){
	
	int index = table.getSelectedRow();
	
	List<TableData> dd = a.database.GetRow(index);
	
	String url = dd.get(3).toString();
	
	url = url.replace("=", "");
	
	String source= "";
	List<Info> infos = null;
	try {
		source = ListLinks.GetSource(url);
		infos = ListLinks.GetImages(url);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    a.files.ClearData();
       
   
    for(Info ns : infos){
    	
    	
    	a.files.addRow();
    	
    	int row = a.files.getRowCount();
    	
    	TableData d = new TableDataInt(row - 1);
    	TableData h0 = new TableDataStr(ns.cat);
    	TableData d0 = new TableDataStr(ns.Id);
    	TableData d1 = new TableDataStr(ns.title);
    	TableData d2 = new TableDataStr(ns.imgs);

    	a.files.setValueAt(d, row -1, 0);
    	a.files.setValueAt(h0, row -1, 1);
    	a.files.setValueAt(d0, row - 1, 2);
    	a.files.setValueAt(d1, row - 1, 3);
    	a.files.setValueAt(d2, row - 1, 4);
    	
    }
	
}

}

class DecoratorsView extends Decorators {
	
public JTable table = null;	

public void PerformSelect(Database fls, int i){

    String s = ((TableDataStr)fls.getValueAt(i, 3)).toString();
	
    
    String url =s.trim();
    
    if(url == "")
    return;
    
    if(url == null)
    	return;
    
    System.out.println(url);
    
    String urls = "";
    
    url = "http://www.youtube.com/watch?v=" + url;
    
    if(url.contains(".js") == true)
    return;
    		
    urls = url;
    

    Application.wb.jwb.navigate(urls);

	
	
}


public void PerformLoad(Application a){
	
	int index = table.getSelectedRow();
	
	List<TableData> dd = a.database.GetRow(index);
	
	String url = dd.get(3).toString();
	
	url = url.replace("=", "");
	
	String source= "";
	List<Info> infos = null;
	try {
		source = ListLinks.GetSource(url);
		infos = ListLinks.GetImages(url);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    a.files.ClearData();
       
   
    for(Info ns : infos){
    	
    	
    	a.files.addRow();
    	
    	int row = a.files.getRowCount();
    	
    	TableData d = new TableDataInt(row - 1);
    	TableData h0 = new TableDataStr(ns.cat);
    	TableData d0 = new TableDataStr(ns.Id);
    	TableData d1 = new TableDataStr(ns.title);
    	TableData d2 = new TableDataStr(ns.imgs);

    	a.files.setValueAt(d, row -1, 0);
    	a.files.setValueAt(h0, row -1, 1);
    	a.files.setValueAt(d0, row - 1, 2);
    	a.files.setValueAt(d1, row - 1, 3);
    	a.files.setValueAt(d2, row - 1, 4);
   	
    }

	
}

}

class DecoratorVideo extends Decorators {

@Override	
public void PerformSelect(Database fls, int i){

	   String s = ((TableDataStr)fls.getValueAt(i, 3)).toString();
		
	    
	    String url =s.trim();
	    
	    if(url == "")
	    return;
	    
	    if(url == null)
	    	return;
	    
	    System.out.println(url);
	    
	    String urls = "";
	    
	    if(url.contains(".js") == true)
	    return;
	    		
	    urls = url;
	    

	    Application.wb.jwb.navigate(urls);

	
	
}

@Override
public void PerformLoad(Application a)
{
    
	int index = table.getSelectedRow();
	
	List<TableData> dd = Application.database.GetRow(index);
	
	String url = dd.get(3).toString();
	
	url = url.replace("=", "");
	
	url = "http://www.youtube.com/watch?v=" + url;
	
	String source= "";
	List<Info> infos = null;
	try {
		source = ListLinks.GetSource(url);
		infos = ListLinks.GetLinks(url);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	a.files.ClearData();
       
     
    for(Info ns : infos){
    	
    	
    	a.files.addRow();
    	
    	int row = a.files.getRowCount();
    	
    	TableData d = new TableDataInt(row - 1);
    	TableData h0 = new TableDataStr(ns.cat);
    	TableData d0 = new TableDataStr(ns.Id);
    	TableData d1 = new TableDataStr(ns.title);
    	TableData d2 = new TableDataStr(ns.imgs);

    	a.files.setValueAt(d, row -1, 0);
    	a.files.setValueAt(h0, row -1, 1);
    	a.files.setValueAt(d0, row - 1, 2);
    	a.files.setValueAt(d1, row - 1, 3);
    	a.files.setValueAt(d2, row - 1, 4);
    	
    	
    }

    a.files.decorator = new Decorators();
    a.files.decorator.table = table;
	
	
}


}

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
    
    Decorators decorator = null;
    
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


public class Application extends JPanel {
    
	
static public DatabaseProxy proxy = new DatabaseProxy();	
	

public DatabaseProxy GetProxy(){
	
	
	if(proxy == null)
		proxy = new DatabaseProxy();
	
	return proxy;
	
	
	
}


static public void UpdateDatabase(){
	
	if(proxy == null)
		return;
	
	//SQLiteSearch.UpdateSearch(proxy.copy);
	
	SQLiteSearch.NewSearch(proxy.copy);
	
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
    
    public Application() {
    	
     
        
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
    
    static int loc = 0;
    
    static int locs = 0;
    
    public JPanel panel;
    
    JTable table;
    
	public static void main(String[] args) {
		
		
		Application a = new Application();
		
		 nativeswing.common.UIUtils.setPreferredLookAndFeel();
		 Toolkit.getDefaultToolkit().setDynamicLayout(true);
		    NativeInterface.open();
		    SwingUtilities.invokeLater(new Runnable() {
		      public void run() {

		    	  a.panel = new JPanel();
			       
		    	  a.panel.setLayout( new BoxLayout(a.panel, BoxLayout.Y_AXIS) );
		    	  
			        database = new Database();
			        
			        a.table = new JTable(database);
			        a.table.setDefaultRenderer(Object.class, new AdvancedRenderer());		        
			        JMenuBar bar = new JMenuBar();
			        
			        JButton row = new JButton("Dodaj Wiersz");
			        JButton col = new JButton("Dodaj Kolumnê");
			        JButton pms = new JButton("Settings");
			        JButton sql = new JButton("Import SQLite");
			        JButton src = new JButton("New SQLite Search");
			        
			        
			        //bar.add(row);
			        //bar.add(col);
			        bar.add(pms);
			        bar.add(sql);
			        bar.add(src);
			        
			       a.panel.add(bar);
			        
			       
			       a.table.setMinimumSize(new Dimension(10000,10000));
			       
			       a.panel.add(new JScrollPane(a.table), BorderLayout.CENTER);

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
			        Color color = UIManager.getColor("Table.gridColor");
			        fls.setGridColor(Color.ORANGE);
			        
			        
			        dfs = new JScrollPane();
			        
		
			        try {
						LoadDefaults();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
			        ListSelectionListener listSelectionListener = new ListSelectionListener() {
			            public void valueChanged(ListSelectionEvent listSelectionEvent) {
			              
			            	int i = list.getSelectedIndex();
			            	
			            	String text = list.getModel().getElementAt(i).toString();
			            	
			            	SetSearchText(text);
			            	
			            }
			          };
			          list.addListSelectionListener(listSelectionListener);

			        
			        
			     	  splitPaner = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	                             panelup, paneldw);
			    	  	splitPaner.setOneTouchExpandable(true);
			    	  	splitPaner.setDividerLocation(250);
			        
			        
			       wb = new SimpleWebBrowser(); 
		
			       
			    	  splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	                             wb, splitPaner);
			    	  	splitPane.setOneTouchExpandable(true);
			    	  	
			    	  	splitPane.setDividerLocation(0.3);
			       
			       
			    	  JSplitPane splitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	                              a.panel, splitPane);
			    	  splitPanes.setOneTouchExpandable(true);
			    	  splitPanes.setDividerLocation(300);

			    	  JPopupMenu popupMenu = new JPopupMenu();
			    	  JMenuItem asHTML = new JMenuItem("Decorator as HTML");
			    	  JMenuItem asVideo = new JMenuItem("Decorator as Video");
			    	  //JMenuItem menuItemRemoveAll = new JMenuItem("HTML");
			    	   
			    	  popupMenu.add(asHTML);
			    	  popupMenu.add(asVideo);
			    	  
			    	  
			    	  a.table.setComponentPopupMenu(popupMenu);			    	  
			    	  
			    	     asHTML.addActionListener(new ActionListener(){
					            public void actionPerformed(ActionEvent ev)
					            {
					                

									files.decorator = new Decorators();
									files.decorator.table = a.table;
									files.decorator.PerformLoad(a);
					            				            	
					            	
					            	
					            }
					        });

			    	     asVideo.addActionListener(new ActionListener(){
					            public void actionPerformed(ActionEvent ev)
					            {
					                
					            	files.decorator = new DecoratorVideo();
					                files.decorator.table = a.table;
					                files.decorator.PerformLoad(a);
				            	
					            	
					            	
					            }
					        });

			    	     
			    	  
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
			            	sql.Settings(true);
			                
			        
			            }
			        });
			        
			        
			        sql.addActionListener(new ActionListener(){
			            public void actionPerformed(ActionEvent ev)
			            {
			            				            	
			            	CreateSQLiteTemplate();
			            	
			            	CreateNewProxy();
			            	
			            	a.table.updateUI();
			            	
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
			        
			                a.table.updateUI();
			                
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
			                    a.panel,
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
			            	
			            	a.table.updateUI();
			            	
			            	SQLiteSearch sql = SQLiteSearch.GetInstance();
			            	
			                List<Info> infos = sql.NewSearch(true);
			                
			                
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
			        
			                a.table.updateUI();
			                
			            }
			        });
			        
	        		    	  
   		          a.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			            public void valueChanged(ListSelectionEvent lse) {
			                if (!lse.getValueIsAdjusting()) {
			                    System.out.println("Selection Changed");
			                    
			                    
			                    int i = a.table.getSelectedRow();
			                    
			                    String s = ((TableDataStr)database.getValueAt(i, 3)).toString();
	
			                    
			                    String url =s.trim();
			                    
			                    if(url == "")
			                    return;
			                    
			                    if(url == null)
			                    	return;
			                    
			                    System.out.println(url);
			                    
			                    
			                    
			                    String urls = "http://www.youtube.com/watch?v=" + url;
		
			                    
			                    if(url.startsWith("=http"))
			                    	urls = url.replace("=", "");
			                    
			                    
			                    wb.jwb.navigate(urls);
			                    
			                 			                    
			                }
			            }

			        });
		    	  
   		          
  		        synup.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent ev)
		            {
		             System.out.println("Synchronization has been performed...");
		             
		            	CreateSQLiteTemplate();
		            			            	
		            	a.table.updateUI();
		            	
		            			            	
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
		        
		                a.table.updateUI();
		                
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
				        
								files.decorator = new DecoratorsView();
								files.decorator.table = a.table;
				                
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
			                    
			                    files.decorator.PerformSelect(files, i);
			                 			                    
			                }
			            }

			        });  
   		          
  		          
  		          
  		          
  		        MenuBar  menu = new MenuBar(); 
  		          
  		        frame = new JFrame("Internet Resources Design Patterns");
  		        
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        ImageIcon img = new ImageIcon("icons/centos.png");
		        
		        frame.setIconImage(img.getImage());
		        
		        
		        JMenuBar menuBar = new JMenuBar();
		         

		        //frame.setJMenuBar(menuBar);
		         
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
	     
		        newAction.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent actionEvent) {
   	
		            	
		            		            }
		        });
		        
		        
		        toolbar = new JToolBar("Applications");
		        
		      
		        
		        JButton btnCalendar = new JButton(new ImageIcon("icons/googlizer.png"));
		        btnCalendar.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                		            	
		            	CreateSQLiteTemplate();
		            	
		            	CreateNewProxy();
		            	
		            	a.table.updateUI();
		            	
		            	String searchTerm = SQLiteSearch.sql.sqlfnd.getText();
		            	
		            	List<Info> infos = GoogleSearchJava.Search(searchTerm);
		            	
		                	                
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
		        
		                a.table.updateUI();
		                
		                proxy.original = infos;
		                
		                proxy.copy = infos;
	            	
		            	
		            }
		        });
		 
		        JButton btnGoogle = new JButton(new ImageIcon("icons/video-television-3.png"));
		        btnGoogle.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {


		            	CreateSQLiteTemplate();
		            	
		            	CreateNewProxy();
		            	
		            	a.table.updateUI();
		            	
		            	SQLiteSearch sql = SQLiteSearch.GetInstance(false);
		            	
		                List<Info> infos = sql.NewSearch(false);
		                
		                
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
		        
		                a.table.updateUI();

		            	
		            }
		        });
		 
		        JButton btnMail = new JButton(new ImageIcon("icons/configure.png"));
		        btnMail.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	
		            	toggledefaults();
	            	
		            	
	          }
		        });
		 
		        JButton btnMessages = new JButton(new ImageIcon("icons/db.png"));
		        btnMessages.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                
		            }
		        });
		 
		        
		        
		        JButton btnPhone = new JButton(new ImageIcon("icons/view-right-new.png"));
		        btnPhone.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		               
		                
		                if(splitPane.getRightComponent().isVisible() == true){		     
		                	loc = splitPane.getDividerLocation();
		                splitPane.setDividerSize(0);
		                	splitPane.getRightComponent().setVisible(false);
		                }
		                else {
		                	splitPane.getRightComponent().setVisible(true);
		                	splitPane.setDividerLocation(loc);
		                	splitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
		                }
	                
		            
		                frame.repaint();
		            }
		        });
		 
		        JButton btnLeft = new JButton(new ImageIcon("icons/view-left-close.png"));
		        btnLeft.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		               
		                if(splitPanes.getLeftComponent().isVisible() == true){		     
		                	locs = splitPanes.getDividerLocation();
		                splitPanes.setDividerSize(0);
		                	splitPanes.getLeftComponent().setVisible(false);
		                }
		                else {
		                	splitPanes.getLeftComponent().setVisible(true);
		                	splitPanes.setDividerLocation(locs);
		                	splitPanes.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
		                }
	                
		            
		                frame.repaint();
		                
		                
		                
		            }
		        });
		        
		        
		        tf = new JTextField(3);
		        tf.setMinimumSize(tf.getPreferredSize());
		        
		        Font font = new Font("Verdana", Font.BOLD, 16);
		        tf.setFont(font);
		        tf.setForeground(Color.BLUE);
		
		        tf.addActionListener(new ActionListener() {
		        	
		        	    public void actionPerformed(ActionEvent e) {
		        	
		        	        System.out.println("Enter pressed");
		
		        	        String text = tf.getText();
		        	        
		        	        try {
								AddDefaults(text);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	
		        	    }
		        	
		        	});

		        
		        
		        toolbar.add(btnCalendar);
		        toolbar.add(btnGoogle);
		        toolbar.add(btnMail);
		        toolbar.add(btnMessages);
		        toolbar.add(btnLeft);
		        toolbar.add(btnPhone);
		        toolbar.add(tf);
		        
		        frame.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		        
		    
		        JPanel statusPanel = new JPanel();
		        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		        frame.add(statusPanel, BorderLayout.SOUTH);
		        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 25));
		        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		        JLabel statusLabel = new JLabel("");
		        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		        statusPanel.add(statusLabel);
	        
		        JButton mt = new JButton("Menu");
		        mt.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                
		            	
		            	toggletools();
		            	
		            }
		        });
		        
		        statusPanel.add(mt);
		        
		        
		        frame.setPreferredSize(new Dimension(950, 800));
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
  		              
  		                		              
  		              
  		                }
  		          }
  		       });
   		          
		    
		      }
		    });
	        
			if(SQLiteSearch.sql == null) {
			
				SQLiteSearch sql = SQLiteSearch.GetInstance();
					
				sql.Close();
			}
		    
			
			toggledefaults();
    }
	
	private void KeyPressed(java.awt.event.KeyEvent evt) {
		  if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
		      // Enter was pressed. Your code goes here.
		   }
		} 
	
	
	static JList list = null;
	
	static JScrollPane dfs = null;
	
	static JSplitPane splitPane = null;
	
	static JSplitPane splitPaner = null;
	
	static JTextField tf = null;
	
	static public void SetSearchText(String text){
		
		tf.setText(text);
		
		if(SQLiteSearch.sql == null)
			SQLiteSearch.GetInstance();
		
		SQLiteSearch.sql.sqlfnd.setText(text);
		
		
	}
	
	static void AddDefaults(String text) throws IOException{
		
		
		File file = new File("defaults.txt");
		
		List ls = org.apache.commons.io.FileUtils.readLines(file);
		
		int index = ls.indexOf(text);
		
		if(index >= 0)
			return;
		
		ls.add(text);
		
		org.apache.commons.io.FileUtils.writeLines(file, ls);
		
		list.setListData(ls.toArray());
		
		
		
		list.validate();
		
		list.repaint();
	}
	
	static void LoadDefaults() throws IOException{
		
		
		if(list != null)
			return;
		
		File file = new File("defaults.txt");
		
		List ls = org.apache.commons.io.FileUtils.readLines(file);
		
		list = new JList();
		
		list.setListData(ls.toArray());
		
        Font font = new Font("Verdana", Font.BOLD, 16);
        
        list.setFont(font);
        list.setForeground(Color.GREEN);

			
		list.setMinimumSize(new Dimension(1000,100));
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
	    dfs = new JScrollPane(list);
	
		
	}
	
	static Boolean search = false;
	
	static public void toggledefaults(){
		
		
		
		
		try {
			LoadDefaults();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(search == false){
		
			splitPane.remove(splitPaner);
		
			splitPane.add(dfs);
		
			splitPane.repaint();
			
			search = true;
		}
		else {
			
			splitPane.remove(dfs);
			
			splitPane.add(splitPaner);
		
			splitPane.repaint();
			
			search = false;
		}
		
		
	}
	
	
	static Application app = null;
	
	
	static public JToolBar toolbar = null;
	
	static public Boolean tools = true;

	static JFrame frame = null;
	
	static public void toggletools(){
		
		
		if(tools == true){
		
			app.frame.setVisible(false);
			
			app.frame.getContentPane().remove(toolbar);
		
			
			
			app.frame.dispose();
			
			app.frame.setUndecorated(true);
			
			app.frame.pack();
			
			app.frame.setLocationRelativeTo(null);
			
			app.frame.setVisible(true);
			
			app.frame.validate();
		
			app.frame.repaint();
		
			
			
			tools = false;
		}
		else {

			app.frame.setVisible(false);
			
			app.frame.getContentPane().add(toolbar, BorderLayout.PAGE_START);
			
			app.frame.dispose();
			
			app.frame.setUndecorated(false);
			
			app.frame.pack();
			
			app.frame.setLocationRelativeTo(null);
			
			app.frame.setVisible(true);
			
			app.frame.validate();
		
			app.frame.repaint();
			
			tools = true;
		}
		
	
	}

}
