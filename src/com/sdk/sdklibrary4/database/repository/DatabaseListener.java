package com.sdk.sdklibrary4.database.repository;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface DatabaseListener {
    boolean isTableContains(String table, String column, String key, String type) throws SQLException;

    boolean removeTable(String table);

    boolean createTable(String table, Map<String, String> columns, String id);

    boolean tableExists(String table) throws SQLException;

    boolean columnExists(String table, String column) throws SQLException;

    public ArrayList<Object> getColumnData(String table, String column, String type) throws SQLException;

    int countRecords(String table) throws SQLException;

    void fillTable(String table, String[] columns, JTable jTable) throws SQLException;

    String createSelectSql(String table, String[] columns);

    void exportTableToExcelFile(JTable table, int columns, String path) throws IOException;
}
