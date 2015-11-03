import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRendererDemo extends JPanel
{
    /*
     * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     *   - Redistributions of source code must retain the above copyright
     *     notice, this list of conditions and the following disclaimer.
     *
     *   - Redistributions in binary form must reproduce the above copyright
     *     notice, this list of conditions and the following disclaimer in the
     *     documentation and/or other materials provided with the distribution.
     *
     *   - Neither the name of Oracle or the names of its
     *     contributors may be used to endorse or promote products derived
     *     from this software without specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
     * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
     * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
     * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
     * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
     * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */
    /**
     * This TableDemo is a modified version of the Sun example. 
     * The model contains an array of Word (a custom class) instead of Objects.
     * This version contains a TableRenderer class.
     */
    private boolean DEBUG = false;

    public TableRendererDemo()
    {
        super(new GridLayout(1, 0));

        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(250, 70));
        table.setFillsViewportHeight(true);
        table.setRowHeight(32);  // height of icon
        //Set up renderer for the cells containing Words.
        table.setDefaultRenderer(Word.class, new SimpleWordRenderer());
        // Uncomment this to use the Advanced Renderer
        //table.setDefaultRenderer(Word.class, new AdvancedWordRenderer());
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    class MyTableModel extends AbstractTableModel
    {
        private String[] columnNames =
        {
            "",
            ""
        };
        private Word[][] data;

        public MyTableModel()
        {
            data = new Word[2][2];
            data[0][0] = new Word("BIG");
            data[1][0] = new Word("TALL");
            data[0][1] = new Word("SMALL");
            data[1][1] = new Word("UNUSUALLYLONG");
        }

        public int getColumnCount()
        {
            return columnNames.length;
        }

        public int getRowCount()
        {
            return data.length;
        }

        public String getColumnName(int col)
        {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col)
        {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @Override
        public Class getColumnClass(int c)
        {
            return getValueAt(0, c).getClass();
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("TableRendererDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableRendererDemo newContentPane = new TableRendererDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    /** This is our new custom class */
    class Word
    {
        // Note these fields are package-private
        String name;
        int rank;

        /** Construct a Word from a given name */
        public Word(String name)
        {
            this.name = name;
            this.rank = name.length();
        }

        public String toString()
        {
            return name;
        }
    }
    /** This is an example of a CellRenderer that tailors the
    * way the output is displayed.
    */
    class SimpleWordRenderer extends DefaultTableCellRenderer
    {
        protected void setValue(Object value)
        {
            Word word = (Word) value;
            String name = word.toString();
            // Long words get a star added to them
            if (word.rank > 9)
            {
                name = name + "*";
            }
            setText(name);
        }
    }

    java.net.URL myurl = getClass().getResource("star.png");
    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(myurl);

    /** This Renderer can display EITHER an icon OR text */
    class AdvancedWordRenderer extends DefaultTableCellRenderer
    {
        public void setValue(Object value)
        {
            Word word = (Word) value;
            String name = word.toString();
            setIcon(null);
            setText(null);
            // Long words are displayed as an icon
            if (word.rank > 9)
            {
                setIcon(icon);
                icon.setDescription("star");  // for Dalbey's testing
            }
            else
            {
                setText(name);
            }
        }
    }
}