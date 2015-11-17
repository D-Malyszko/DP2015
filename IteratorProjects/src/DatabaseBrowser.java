import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class DatabaseBrowser extends JPanel {

  protected Connection connection;

  protected JComboBox catalogBox;

  protected JComboBox schemaBox;

  protected JComboBox tableBox;

  protected JTable table = new JTable();

  public static void main(String[] args) throws Exception {
    
    DatabaseBrowser db = new DatabaseBrowser();
  }

  public DatabaseBrowser() throws Exception {
    //ConnectionDialog cd = new ConnectionDialog(this);
    //cd.pack();
    //cd.setVisible(true);
    
    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:designs.sql","","");
    
    //connection = cd.getConnection();
    
    BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
    
    Container pane = this;//getContentPane();
    pane.add(getSelectionPanel(), bl);
    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    refreshTable();
    pane.add(new JScrollPane(table), bl);

    
    setSize(300, 450);

    //setVisible(true);
  }

  public String database = "searches.sql";
  
  
  public void LoadDatabase() throws ClassNotFoundException, SQLException{
	  
	   Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection("jdbc:sqlite:" + database,"","");
	    populateTableBox();
  	    refreshTable();
	  
	  
  }
  
  
  protected JPanel getSelectionPanel() {
    JPanel panel = new JPanel();
    panel.add(new JLabel("Catalog"));
    panel.add(new JLabel("Schema"));
    panel.add(new JLabel("Table"));

    catalogBox = new JComboBox();
    populateCatalogBox();
    panel.add(catalogBox);
    schemaBox = new JComboBox();
    populateSchemaBox();
    panel.add(schemaBox);
    tableBox = new JComboBox();
    populateTableBox();
    panel.add(tableBox);

    catalogBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        String newCatalog = (String) (catalogBox.getSelectedItem());
        try {
          connection.setCatalog(newCatalog);
        } catch (Exception e) {
        }
        populateSchemaBox();
        populateTableBox();
        refreshTable();
      }
    });

    schemaBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        populateTableBox();
        refreshTable();
      }
    });

    tableBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        refreshTable();
      }
    });
    return panel;
  }

  protected void populateCatalogBox() {
    try {
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getCatalogs();
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(1));
      }
      rset.close();
      catalogBox.setModel(new DefaultComboBoxModel(values));
      catalogBox.setSelectedItem(connection.getCatalog());
      catalogBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      catalogBox.setEnabled(false);
    }
  }

  protected void populateSchemaBox() {
    try {
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getSchemas();
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(1));
      }
      rset.close();
      schemaBox.setModel(new DefaultComboBoxModel(values));
      schemaBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      schemaBox.setEnabled(false);
    }
  }

  protected void populateTableBox() {
    try {
      String[] types = { "TABLE" };
      String catalog = connection.getCatalog();
      String schema = (String) (schemaBox.getSelectedItem());
      DatabaseMetaData dmd = connection.getMetaData();
      ResultSet rset = dmd.getTables(catalog, schema, null, types);
      Vector values = new Vector();
      while (rset.next()) {
        values.addElement(rset.getString(3));
      }
      rset.close();
      tableBox.setModel(new DefaultComboBoxModel(values));
      tableBox.setEnabled(values.size() > 0);
    } catch (Exception e) {
      tableBox.setEnabled(false);
    }
  }

  protected void refreshTable() {
    String catalog = (catalogBox.isEnabled() ? catalogBox.getSelectedItem().toString() : null);
    String schema = (schemaBox.isEnabled() ? schemaBox.getSelectedItem().toString() : null);
    String tableName = (String) tableBox.getSelectedItem();
    if (tableName == null) {
      table.setModel(new DefaultTableModel());
      return;
    }
    String selectTable = (schema == null ? "" : schema + ".") + tableName;
    if (selectTable.indexOf(' ') > 0) {
      selectTable = "\"" + selectTable + "\"";
    }
    try {
      Statement stmt = connection.createStatement();
      ResultSet rset = stmt.executeQuery("SELECT * FROM " + selectTable);
      table.setModel(new ResultSetTableModel(rset));
    } catch (Exception e) {
    }
  }



}
class ConnectionDialog extends JDialog {

  protected JTextField useridField;

  protected JTextField passwordField;

  protected JTextField urlField;

  protected boolean canceled;

  protected Connection connect;

  public ConnectionDialog(JPanel f) {
    //super(f, "Connect To Database", true);
    buildDialogLayout();
    setSize(300, 200);
  }

  public Connection getConnection() {
    setVisible(true);
    return connect;
  }

  protected void buildDialogLayout() {
	  
	 
    Container pane = getContentPane();
    BoxLayout mgr = new BoxLayout(pane,BoxLayout.Y_AXIS);
    //pane.setLayout(mgr);
    pane.setLayout(mgr);
    JPanel p0 = new JPanel();
    p0.add(new JLabel("Userid:"), BorderLayout.CENTER);
    useridField = new JTextField(10);
    p0.add(useridField);
    
    pane.add(p0, mgr);
    
    JPanel p1 = new JPanel();
    p1.add(new JLabel("Password:"), BorderLayout.CENTER);
    passwordField = new JTextField(10);
    p1.add(passwordField);
    pane.add(p1, mgr);
    
    
    JPanel p2 = new JPanel();
    p2.add(new JLabel("URL:"), BorderLayout.CENTER);
    urlField = new JTextField(15);
    p2.add(urlField);
    pane.add(p2, mgr);
    
 
    
    
    pane.add(getButtonPanel(), BorderLayout.CENTER);
    
    //this.getContentPane().add(pane);
  }

  protected JButton getButtonPanel() {
    JPanel panel = new JPanel();
    JButton btn = new JButton("Ok");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        onDialogOk();
      }
    });
    //panel.add(btn);
    return btn;
  }

  protected void onDialogOk() {
    if (attemptConnection()) {
      setVisible(false);
    }
  }

  protected boolean attemptConnection() {
    try {
      connect = DriverManager.getConnection(urlField.getText(), useridField.getText(),
          passwordField.getText());
      return true;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Error connecting to " + "database: " + e.getMessage());
    }
    return false;
  }

}
class ResultSetTableModel extends AbstractTableModel {

  protected Vector columnHeaders;

  protected Vector tableData;

  public ResultSetTableModel(ResultSet rset) throws SQLException {
    Vector rowData;
    ResultSetMetaData rsmd = rset.getMetaData();
    int count = rsmd.getColumnCount();
    columnHeaders = new Vector(count);
    tableData = new Vector();
    for (int i = 1; i <= count; i++) {
      columnHeaders.addElement(rsmd.getColumnName(i));
    }
    while (rset.next()) {
      rowData = new Vector(count);
      for (int i = 1; i <= count; i++) {
        rowData.addElement(rset.getObject(i));
      }
      tableData.addElement(rowData);
    }
  }

  public int getColumnCount() {
    return columnHeaders.size();
  }

  public int getRowCount() {
    return tableData.size();
  }

  public Object getValueAt(int row, int column) {
    Vector rowData = (Vector) (tableData.elementAt(row));
    return rowData.elementAt(column);
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public String getColumnName(int column) {
    return (String) (columnHeaders.elementAt(column));
  }

}
