package com.sdk.sdklibrary4.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.print.PrinterException;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public interface GraphicUser {

    void setFrameCenter(JFrame var1);

    void setDialogCenter(JDialog var1);

    void setTableDataModel(DefaultTableModel var1, JTable var2);

    String getComponentPosition(Component var1);

    void setTableNoneEditable(JTable var1);

    void setTextFieldLimit(JTextField var1, int var2);

    void setTextFieldInputType(JTextField var1, String var2);

    void setFrameCloseESC(JFrame var1);

    void setDialogCloseESC(JDialog var1);

    void makeDialogMovable(JDialog var1);

    void makeFrameMovable(JFrame var1);

    boolean printTable(JTable var1, String var2, String var3) throws PrinterException;

    boolean printTextArea(JTextArea var1) throws PrinterException;

    long countTableRows(JTable var1);

    long countTableColumns(JTable var1);

    boolean clearTable(JTable var1, boolean var2);

    void clearComponents(JFrame var1, String var2, boolean var3);

    void clearComponents(JDialog var1, String var2, boolean var3);

    void clearComponents(JPanel var1, String var2, boolean var3);

    void clearList(JList var1);

    boolean addToList(JList var1, String[] var2);

    Color showColorDialog(Component var1, String var2, Color var3);

    void setComponentDirection(Component var1, String var2);

    void setComponentsDirection(Component[] var1, String var2);

    void setComponentsDirection(JFrame var1, String var2);

    void setComponentsDirection(JDialog var1, String var2);

    void setComponentsDirection(JPanel var1, String var2);
}
