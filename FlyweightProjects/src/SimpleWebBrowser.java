/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */


import java.util.*;
import java.util.HashMap;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nativeswing.common.Utils;
import nativeswing.NSComponentOptions;
import nativeswing.NSOption;
import nativeswing.NativeInterface;
import nativeswing.components.JWebBrowser;
import nativeswing.*;

/**
 * @author Christopher Deckers
 */
public class SimpleWebBrowser extends JPanel {

	public JWebBrowser jwb = null;
	
  public SimpleWebBrowser() {
   
	
	  super(new BorderLayout());
	  
	  nativeswing.NSOption nc = NSComponentOptions.destroyOnFinalization();
    
	  
	  
	  NativeInterface.open();
	  
	  JPanel webBrowserPanel = new JPanel(new BorderLayout());
    //webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
    final JWebBrowser webBrowser = new JWebBrowser(nc);
    webBrowser.navigate("http://www.google.com");
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    add(webBrowserPanel, BorderLayout.CENTER);
    // Create an additional bar allowing to show/hide the menu bar of the web browser.
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
    JCheckBox menuBarCheckBox = new JCheckBox("Menu Bar", webBrowser.isMenuBarVisible());
    menuBarCheckBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        webBrowser.setMenuBarVisible(e.getStateChange() == ItemEvent.SELECTED);
      }
    });
    buttonPanel.add(menuBarCheckBox);
    add(buttonPanel, BorderLayout.SOUTH);
    jwb = webBrowser;
    
  }

  /* Standard main method to try that test as a standalone application. */
  public static void main(String[] args) {
    nativeswing.common.UIUtils.setPreferredLookAndFeel();
    NativeInterface.open();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("DJ Native Swing Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SimpleWebBrowser(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
      }
    });
    NativeInterface.runEventPump();
  }

}