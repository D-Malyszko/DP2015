/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package nativeswing.components.internal;

import nativeswing.components.JWebBrowser;


/**
 * @author Christopher Deckers
 */
public interface INativeMozillaXPCOM {

  public Object getWebBrowser(JWebBrowser webBrowser);

  public boolean initialize();

  public Object pack(Object o, boolean isNativeSide);

  public Object unpack(Object o);

}
