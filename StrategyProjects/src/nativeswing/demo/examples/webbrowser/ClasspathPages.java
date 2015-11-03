/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package nativeswing.demo.examples.webbrowser;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nativeswing.common.Utils;
import nativeswing.common.WebServer;
import nativeswing.NativeInterface;
import nativeswing.components.JWebBrowser;

/**
 * @author Christopher Deckers
 */
public class ClasspathPages {

  public static JComponent createContent() {
    JWebBrowser webBrowser = new JWebBrowser();
    webBrowser.navigate(WebServer.getDefaultWebServer().getClassPathResourceURL(ClasspathPages.class.getName(), "resource/page1.html"));
    webBrowser.setBarsVisible(false);
    return webBrowser;
  }

  /* Standard main method to try that test as a standalone application. */
  public static void main(String[] args) {
    NativeInterface.open();
    nativeswing.common.UIUtils.setPreferredLookAndFeel();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("DJ Native Swing Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(createContent(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
      }
    });
    NativeInterface.runEventPump();
  }

}
