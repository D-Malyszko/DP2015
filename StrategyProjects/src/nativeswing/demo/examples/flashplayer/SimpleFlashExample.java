/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package nativeswing.demo.examples.flashplayer;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nativeswing.common.Utils;
import nativeswing.NativeInterface;
import nativeswing.components.JFlashPlayer;

/**
 * @author Christopher Deckers
 */
public class SimpleFlashExample {

  public static JComponent createContent() {
    JFlashPlayer flashPlayer = new JFlashPlayer();
    flashPlayer.load(SimpleFlashExample.class, "resource/Movement-pointer_or_click.swf");
    return flashPlayer;
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
