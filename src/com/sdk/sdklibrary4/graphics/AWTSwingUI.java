package com.sdk.sdklibrary4.graphics;

import com.sdk.sdklibrary4.data.structures.StringList;
import com.sdk.sdklibrary4.data.types.Strings;
import com.sdk.sdklibrary4.data.types.Characters;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.PrinterException;
import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.JTable.PrintMode;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class AWTSwingUI implements GraphicUser {

    public AWTSwingUI() {
    }

    public AWTSwingUI(boolean enableSystemLookAndFeel) {
        if (enableSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setFrameCenter(JFrame frame) {
        frame.pack();
        frame.setLocationRelativeTo((Component) null);
    }

    @Override
    public void setDialogCenter(JDialog dialog) {
        dialog.pack();
        dialog.setLocationRelativeTo((Component) null);
    }

    @Override
    public void setTableDataModel(DefaultTableModel dtm, JTable table) {
        table.setModel(dtm);
    }

    public String getComponentPosition(Component component) {
        Dimension dimension = component.getSize();
        return dimension.getWidth() + "," + dimension.getHeight();
    }

    @Override
    public void setTableNoneEditable(JTable table) {
        table.setDefaultEditor(Object.class, (TableCellEditor) null);
    }

    @Override
    public void setTextFieldLimit(final JTextField textField, final int limit) {
        final Strings strings = new Strings();
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (textField.getText().length() == limit) {
                    e.consume();
                } else if (textField.getText().length() > limit) {
                    textField.setText(strings.getEmptyString());
                }

            }
        });
    }

    @Override
    public void setTextFieldInputType(JTextField textField, final String mode) {
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                if (mode.equals("number")) {
                    if (Character.isDigit(e.getKeyChar())) {
                        e.consume();
                    }
                } else if (mode.equals("string") && !Character.isAlphabetic(e.getKeyChar())) {
                    e.consume();
                }

            }
        });
    }

    @Override
    public void setFrameCloseESC(final JFrame frame) {
        frame.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(27, 0), "Cancel");
        frame.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    @Override
    public void setDialogCloseESC(final JDialog dialog) {
        dialog.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(27, 0), "Cancel");
        dialog.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    @Override
    public void makeDialogMovable(final JDialog dialog) {
        final Point point = new Point();
        dialog.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        dialog.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = dialog.getLocation();
                dialog.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
    }

    @Override
    public void makeFrameMovable(final JFrame frame) {
        final Point point = new Point();
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
    }

    @Override
    public boolean printTable(JTable table, String title, String format) throws PrinterException {
        if (!Objects.isNull(format)) {
            MessageFormat footer = new MessageFormat(format);
            return table.print(PrintMode.FIT_WIDTH, new MessageFormat(title), footer);
        } else {
            return table.print(PrintMode.FIT_WIDTH);
        }
    }

    @Override
    public boolean printTextArea(JTextArea textArea) throws PrinterException {
        return !Objects.isNull(textArea) ? textArea.print() : false;
    }

    @Override
    public long countTableRows(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        return (long) dtm.getRowCount();
    }

    @Override
    public long countTableColumns(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        return (long) dtm.getColumnCount();
    }

    @Override
    public boolean clearTable(JTable table, boolean clearColumns) {
        try {
            if (clearColumns) {
                table.setModel(new DefaultTableModel());
            } else {
                DefaultTableModel dm = (DefaultTableModel) table.getModel();

                while (dm.getRowCount() > 0) {
                    dm.removeRow(0);
                }
            }

            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    @Override
    public void clearComponents(JFrame frame, String component, boolean setBlackColor) {
        Strings strings = new Strings();
        if (new StringList(true).add(new String[]{"field", "label", "combo"}).contains(component)) {
            Arrays.asList(frame.getContentPane().getComponents()).forEach((cmp) -> {
                if (component.equals("field")) {
                    if (cmp instanceof JTextField) {
                        ((JTextField) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("label")) {
                    if (cmp instanceof JLabel) {
                        ((JLabel) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("combo") && cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);
                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }

            });
        }
    }

    @Override
    public void clearComponents(JDialog dialog, String component, boolean setBlackColor) {
        Strings strings = new Strings();
        if (new StringList(true).add(new String[]{"field", "label", "combo"}).contains(component)) {
            Arrays.asList(dialog.getContentPane().getComponents()).forEach((cmp) -> {
                if (component.equals("field")) {
                    if (cmp instanceof JTextField) {
                        ((JTextField) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("label")) {
                    if (cmp instanceof JLabel) {
                        ((JLabel) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("combo") && cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);
                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }

            });
        }
    }

    @Override
    public void clearComponents(JPanel panel, String component, boolean setBlackColor) {
        Strings strings = new Strings();
        if (new StringList(true).add(new String[]{"field", "label", "combo"}).contains(component)) {
            Arrays.asList(panel.getComponents()).forEach((cmp) -> {
                if (component.equals("field")) {
                    if (cmp instanceof JTextField) {
                        ((JTextField) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("label")) {
                    if (cmp instanceof JLabel) {
                        ((JLabel) cmp).setText(strings.getEmptyString());
                        if (setBlackColor) {
                            cmp.setForeground(Color.black);
                        }
                    }
                } else if (component.equals("combo") && cmp instanceof JComboBox) {
                    ((JComboBox) cmp).setSelectedIndex(0);
                    if (setBlackColor) {
                        cmp.setForeground(Color.black);
                    }
                }

            });
        }
    }

    @Override
    public void clearList(JList list) {
        DefaultListModel model = new DefaultListModel();
        model.clear();
        list.setModel(model);
    }

    @Override
    public boolean addToList(JList list, String[] items) {
        try {
            if (Objects.isNull(items)) {
                return false;
            } else if (items.length == 0) {
                return true;
            } else {
                ListModel listModel = list.getModel();
                DefaultListModel defaultListModel = new DefaultListModel();

                for (int j = 0; j < listModel.getSize(); j++) {
                    defaultListModel.addElement(listModel.getElementAt(j));
                }

                for (int i = 0; i < items.length; ++i) {
                    String item = items[i];
                    defaultListModel.addElement(item);
                }

                list.setModel(defaultListModel);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Color showColorDialog(Component component, String title, Color color) {
        return JColorChooser.showDialog(component, title, Color.CYAN);
    }

    @Override
    public void setComponentDirection(Component component, String direction) {
        if (!Objects.isNull(component)) {
            byte var4 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var4 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var4 = 0;
                    }
            }

            switch (var4) {
                case 0:
                    component.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    component.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        }
    }

    @Override
    public void setComponentsDirection(Component[] components, String direction) {
        if (!Objects.isNull(components)) {
            Component[] var3 = components;
            int var4 = components.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Component component = var3[var5];
                byte var8 = -1;
                switch (direction.hashCode()) {
                    case 3317767:
                        if (direction.equals("left")) {
                            var8 = 1;
                        }
                        break;
                    case 108511772:
                        if (direction.equals("right")) {
                            var8 = 0;
                        }
                }

                switch (var8) {
                    case 0:
                        component.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                        break;
                    case 1:
                        component.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                }
            }

        }
    }

    @Override
    public void setComponentsDirection(JFrame frame, String direction) {
        Arrays.asList(frame.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }

    @Override
    public void setComponentsDirection(JDialog dialog, String direction) {
        Arrays.asList(dialog.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }

    @Override
    public void setComponentsDirection(JPanel panel, String direction) {
        Arrays.asList(panel.getComponents()).forEach((cmp) -> {
            byte var3 = -1;
            switch (direction.hashCode()) {
                case 3317767:
                    if (direction.equals("left")) {
                        var3 = 1;
                    }
                    break;
                case 108511772:
                    if (direction.equals("right")) {
                        var3 = 0;
                    }
            }

            switch (var3) {
                case 0:
                    cmp.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    break;
                case 1:
                    cmp.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            }

        });
    }
}
