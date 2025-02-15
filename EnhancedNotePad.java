import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EnhancedNotePad extends JFrame implements ActionListener, WindowListener {
    JTextArea txt = new JTextArea();
    File fname;
    UndoManager undoManager = new UndoManager();
    boolean darkMode = false;

    public EnhancedNotePad() {
        Font fnt = new Font("Arial", Font.PLAIN, 15);
        Container con = getContentPane();
        JMenuBar m = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu view = new JMenu("View");
        JMenu format = new JMenu("Format");
        JMenu help = new JMenu("Help");

        con.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(txt);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        txt.setFont(fnt);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.getDocument().addUndoableEditListener(undoManager);

        con.add(scroll);

        createMenuItem(file, "New");
        createMenuItem(file, "Open");
        createMenuItem(file, "Save");
        createMenuItem(file, "Save As");
        file.addSeparator();
        createMenuItem(file, "Exit");

        createMenuItem(edit, "Undo");
        createMenuItem(edit, "Redo");
        createMenuItem(edit, "Cut");
        createMenuItem(edit, "Copy");
        createMenuItem(edit, "Paste");
        createMenuItem(edit, "Find and Replace");

        createMenuItem(view, "Toggle Dark Mode");
        createMenuItem(format, "Font Size");
        createMenuItem(format, "Font Color");

        createMenuItem(help, "About NotePad");

        m.add(file);
        m.add(edit);
        m.add(view);
        m.add(help);
        m.add(format);

        setJMenuBar(m);
        addWindowListener(this);

        setSize(600, 600);
        setTitle("Untitled - Notepad");
        setVisible(true);
    }

    public void createMenuItem(JMenu jm, String text) {
        JMenuItem ji = new JMenuItem(text);
        ji.addActionListener(this);
        jm.add(ji);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Font Size":
                String sizeStr = JOptionPane.showInputDialog(this, "Enter Font Size:");
                try {
                    int size = Integer.parseInt(sizeStr);
                    txt.setFont(new Font("Arial", Font.PLAIN, size));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid size entered", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Font Color":
                Color newColor = JColorChooser.showDialog(this, "Choose Font Color", txt.getForeground());
                if (newColor != null) {
                    txt.setForeground(newColor);
                }
                break;
            case "Undo":
                if (undoManager.canUndo()) undoManager.undo();
                break;
            case "Redo":
                if (undoManager.canRedo()) undoManager.redo();
                break;
            case "Toggle Dark Mode":
                darkMode = !darkMode;
                txt.setBackground(darkMode ? Color.DARK_GRAY : Color.WHITE);
                txt.setForeground(darkMode ? Color.WHITE : Color.BLACK);
                break;
            case "Find and Replace":
                findAndReplace();
                break;
            case "About NotePad":
                JOptionPane.showMessageDialog(this, "Enhanced Notepad by Harini", "Notepad", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public void findAndReplace() {
        String find = JOptionPane.showInputDialog(this, "Find:");
        String replace = JOptionPane.showInputDialog(this, "Replace with:");
        if (find != null && replace != null) {
            txt.setText(txt.getText().replaceAll(find, replace));
        }
    }

    public static void main(String[] args) {
        new EnhancedNotePad();
    }
}
