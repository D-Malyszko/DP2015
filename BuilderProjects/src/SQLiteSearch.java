
import java.io.File;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sql2o.Connection;
import org.sql2o.Sql2o;


public final class SQLiteSearch {

	
	static public SQLiteSearch sql = null;
	
	
	private SQLiteSearch() {
		
		if(frame == null)
		frame = createPanelSQLite();
    }

    public static void main(final String[] args) throws UnknownHostException {
        
    	       
        List<Info> infos = Search.Search("design patterns");
   
        Sql2o sql2o = new Sql2o("jdbc:sqlite:hello.db","","");

         
        String sql = "SELECT * " + "FROM infos ";

        Connection con = sql2o.open();
      
        final String insertSql = "insert into infos(id, title, imgs) values (:id, :title, :imgs)";
        
        for(Info ns: infos)
        {
            con.createQuery(insertSql)
        	    .addParameter("id", ns.Id)
        	    .addParameter("title", ns.title)
        	    .addParameter("imgs", ns.imgs)
        	    .executeUpdate();
        }
        
    }

    public void Settings(){
  	
    	if(sql == null)
    	  	sql = this;
    
    	if(sql.frame == null)
    	 sql.frame = createPanelSQLite();
    	
    	sql.frame.setVisible(true);    	
    	
    }
    
   public List<Info> NewSearch() {
        
         
	   String name = sql.sqlfnd.getText();
	   
        List<Info> infos = Search.Search(name);
   
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sql.sqlbase.getText() + "" ,"","" );

        Connection con = sql2o.open();
        
        final String insertSql = "insert into infos(id, title, imgs, cat) values (:id, :title, :imgs, :cat)";
        
        for(Info ns: infos)
        {
        	ns.cat = name;
        	
            con.createQuery(insertSql)
        	    .addParameter("id", ns.Id)
        	    .addParameter("title", ns.title)
        	    .addParameter("imgs", ns.imgs)
        	    .addParameter("cat", ns.cat)
        	    .executeUpdate();
        }
     
     
        
        return infos;
    }

   static public List<Info> UpdateSearch(List<Info> infos) {
       
       
	    String updateSql = "update infos set cat = :cat, title = :title, imgs = :imgs where id = :id";
	   
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sql.sqlbase.getText() + "" ,"","" );

        Connection con = sql2o.open();
        
       
        for(Info ns: infos)
        {
        	
            con.createQuery(updateSql)
        	    .addParameter("id", ns.Id)
        	    .addParameter("title", ns.title)
        	    .addParameter("imgs", ns.imgs)
        	    .addParameter("cat", ns.cat)
        	    .executeUpdate();
        }
    
        
        return infos;
    }

   
   public List<String> listFilesForFolder(final File folder) {
	    
	   List<String> files = new ArrayList();
	   
	   for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	           
	        	files.add(fileEntry.getName() + " - folder");
	        } else {
	            System.out.println(fileEntry.getName());
	            files.add(fileEntry.getName());
	        }
	    }
	   
	   return files;
	   
	}

//	final File folder = new File("/home/you/Desktop");
//	listFilesForFolder(folder);
   
    
   public void CreateNewDatabase(){
	
       Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sql.sqlbase.getText() + "" ,"","" );

       final String createSql = "create table infos(cat varchar(500), id varchar(500), title varchar(500), imgs varchar(500))";
       
       sql2o.open().createQuery(createSql).executeUpdate();
       
      final String createSqlc = "create table categories(cat varchar(500), id varchar(500))";
       
       sql2o.open().createQuery(createSqlc).executeUpdate();
	   
   }

   public List<Info> GetInfos() {
        
   
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sql.sqlbase.getText() + "","","");

       
        String sql = "SELECT * " + "FROM infos ";

        Connection con = sql2o.open();
        
            List<Info> infos2 = con.createQuery(sql)
                .executeAndFetch(Info.class);
        
            return infos2;
            
    }
   
   public List<String> GetCategories() {
       
	   
	   List<Info> infos = GetInfos();
	   
	   
	   //List<Categories> cc = new ArrayList<Categories>();
	   

	   ArrayList<String> cats = new ArrayList<String>();
		   
		
	   
	   
	   for(Info info : infos){
		   
		   
		   String name = info.cat;
		   
		   int i = cats.indexOf(name);
		   
		   if(i < 0) {
			   cats.add(name);
			   
     
		   }

     
	   }
           
           return cats;
           
   }
   
  static public List<String> GetCategories(List<Info> infos) {
       
	   
  

	   ArrayList<String> cats = new ArrayList<String>();
		   
		
	   
	   
	   for(Info info : infos){
		   
		   
		   String name = info.cat;
		   
		   int i = cats.indexOf(name);
		   
		   if(i < 0) {
			   cats.add(name);
			   
     
		   }

     
	   }
           
           return cats;
           
   }
   

	JPanel SQLite;
	JTextField sqlbase;
	
	JTextField sqllab;
	JList sqlfs;
	
	JTextField sqllabs;
	JTextArea sqlars;
	JButton sqlcrs;
	JButton sqlcct;
	JButton sqllds;
	JTextField sqlfnd;
	JList sqlcat;
	JButton sqlcatb;
	
	static JFrame frame;
	
	DefaultListModel<String> lm;
	
	public static SQLiteSearch GetInstance(){
		
		if(sql == null)
			sql = new SQLiteSearch();
		
		frame.setVisible(true);
		
		
		
		sql.LoadCategories();
		
		return sql;
		
	}
	
	
	public void LoadCategories(){
		
		String fnd = sqlfnd.getText();
		
    	List<String> c = GetCategories();
    	
    	lm.clear();
    	
    	for(String cc : c){
    		
    		lm.addElement(cc.toString());
		
		
	}
    	
    	sqlfnd.setText(fnd);
	}
	
	public JFrame createPanelSQLite()
	{
		JFrame frame = new JFrame("Database file - categories");
		
	    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.frame = frame;
		
		SQLite = new JPanel();
		SQLite.setLayout(new BoxLayout(SQLite, BoxLayout.Y_AXIS));
		SQLite.setPreferredSize( new Dimension( 400, 300 ) );
		SQLite.setMaximumSize( new Dimension( 10000, 2000 ) );
		
		sqlbase = new JTextField(10);
		sqlbase.setText("design.sql");
		sqlbase.setMaximumSize(new Dimension(20000, 25));
		SQLite.add(sqlbase, BorderLayout.CENTER );
		
		sqllabs = new JTextField(10);
		sqllabs.setText("Enter datbase file name");
		sqllabs.setEditable(false);
		sqllabs.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqllabs, BorderLayout.CENTER);
		
		
		JScrollPane scrollPane = new JScrollPane();
		
		DefaultListModel lms = new DefaultListModel();
		sqlfs = new JList(lms);
		lms.addElement("file");
		lms.addElement("file");
		lms.addElement("file");
		lms.addElement("file");
		sqlfs.setMinimumSize(new Dimension(20000, 50));
		
		int cellWidth = 20000;
	    sqlfs.setFixedCellWidth(cellWidth);
	    int cellHeight = 18;
	    sqlfs.setFixedCellHeight(cellHeight);
		
		
	    scrollPane.setViewportView(sqlfs);
	    
		SQLite.add(scrollPane, BorderLayout.CENTER);
    
		sqllab = new JTextField(10);
		sqllab.setText("Database table");
		sqllab.setEditable(false);
		sqllab.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqllab, BorderLayout.CENTER);
		
		
		sqlars = new JTextArea();
		sqlars.setText("create table infos(id string, title string, imgs string, cat string)");
		sqlars.setEditable(false);
		sqlars.setMaximumSize(new Dimension(20000, 25));
		BorderLayout border = new BorderLayout();
		border.setVgap(15);
		SQLite.add(sqlars, border);

		sqlcct = new JButton("Create new SQLite database file");
		sqlcct.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqlcct, BorderLayout.WEST);
		
		
		sqlfnd = new JTextField(10);
		sqlfnd.setMaximumSize(new Dimension(20000, 25));
		SQLite.add(sqlfnd, BorderLayout.CENTER);
		
		
		sqllds = new JButton("View search results");
		sqllds.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqllds, BorderLayout.WEST);
		
		lm = new DefaultListModel();
		sqlcat = new JList(lm);
		lm.addElement("test");
		lm.addElement("test");
		lm.addElement("test");
		lm.addElement("test");
		sqlcat.setMinimumSize(new Dimension(20000, 50));
		SQLite.add(sqlcat, BorderLayout.CENTER);
		cellWidth = 20000;
	    sqlcat.setFixedCellWidth(cellWidth);
	    cellHeight = 18;
	    sqlcat.setFixedCellHeight(cellHeight);
		
		sqlcatb = new JButton("Get categories");
		sqlcatb.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqlcatb, BorderLayout.WEST);
		
		sqlcrs = new JButton("Save to SQLite database");
		sqlcrs.setMaximumSize(new Dimension(20000, 20));
		SQLite.add(sqlcrs, BorderLayout.WEST);
		
		
		frame.getContentPane().add(SQLite);
		
		frame.setSize(new Dimension(500,600));
		frame.pack();
		frame.setVisible(true);
		
				
	     sqlcrs.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent ev)
	            {
	            	
	            	program04.UpdateDatabase();
	            	
	            }
	        });
		
	     sqlcct.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent ev)
	            {
	                CreateNewDatabase();
	            }
	        });
	     
	     
	     sqllds.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent ev)
	            {
	            }
	        });
	     
	     sqlcatb.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent ev)
	            {
	            	List<String> c = GetCategories();
	            	
	            	lm.clear();
	            	
	            	for(String cc : c){
	            		
	            		lm.addElement(cc.toString());
	            		
	            	
	            	
	            }
	            }
	        });
	    
	     ListSelectionListener listSelectionListeners = new ListSelectionListener() {
	         public void valueChanged(ListSelectionEvent listSelectionEvent) {
	           //System.out.print("First index: " + listSelectionEvent.getFirstIndex());
	           //System.out.print(", Last index: " + listSelectionEvent.getLastIndex());
	           boolean adjust = listSelectionEvent.getValueIsAdjusting();
	           //System.out.println(", Adjusting? " + adjust);
	           if (!adjust) {
	             
	             String s  = (String) sqlfs.getSelectedValue();
	             
	             sqlbase.setText(s);
	         }
	         }
	       };
	       sqlfs.addListSelectionListener(listSelectionListeners);
	     
	     
	     
	     ListSelectionListener listSelectionListener = new ListSelectionListener() {
	         public void valueChanged(ListSelectionEvent listSelectionEvent) {
	           //System.out.print("First index: " + listSelectionEvent.getFirstIndex());
	           //System.out.print(", Last index: " + listSelectionEvent.getLastIndex());
	           boolean adjust = listSelectionEvent.getValueIsAdjusting();
	           //System.out.println(", Adjusting? " + adjust);
	           if (!adjust) {
	             
	             String s  = (String) sqlcat.getSelectedValue();
	             
	             sqlfnd.setText(s);
	         }
	         }
	       };
	       sqlcat.addListSelectionListener(listSelectionListener);

	       String file = Paths.get(".").toAbsolutePath().normalize().toString();
	       
	       File fs = new File(file);
	       
	       List<String> f = listFilesForFolder(fs);
	       
	       lms.clear();
	       
	       for(String s : f){
	    	   
	    	   lms.addElement(s);
	    	   
	       }
	       
	       
	       
  		return frame;

	}
    
}

//@Entity("Info")
//class Info {
//	
//	public String title;
//	
//	public String Id;
//	
//	public String imgs;
//	
//	
//public Info(String title, String id, String imgs){
//	
//	this.title = title;
//	
//	this.Id = id;
//	
//	this.imgs = imgs;
//}
//	
//public String ToString(){
//	return Id + " - " + title;
//}
//
//	
//}
