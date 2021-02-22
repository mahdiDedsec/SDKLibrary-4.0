package com.sdk.sdklibrary4.database.repository;

import com.sdk.sdklibrary4.data.types.Strings;
import com.sdk.sdklibrary4.database.connections.MicrosoftAccessConnection;
import com.sdk.sdklibrary4.database.connections.MysqlConnection;
import com.sdk.sdklibrary4.database.connections.SqlServerConnection;
import com.sdk.sdklibrary4.database.connections.SqliteConnection;
import com.sdk.sdklibrary4.graphics.AWTSwingUI;
import net.proteanit.sql.DbUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabaseRepository implements DatabaseListener {
    private Connection connection;

    public DatabaseRepository(MicrosoftAccessConnection microsoftAccessConnection) {
        connection = microsoftAccessConnection.getConnection();
    }

    public DatabaseRepository(SqliteConnection sqLiteConnection) {
        connection = sqLiteConnection.getConnection();
    }

    public DatabaseRepository(MysqlConnection mySQLConnection) {
        connection = mySQLConnection.getConnection();
    }

    public DatabaseRepository(SqlServerConnection sqlServerConnection) {
        connection = sqlServerConnection.getConnection();
    }

    public DatabaseRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean isTableContains(String table, String column, String key, String type) throws SQLException {
        return false;
    }

    @Override
    public boolean removeTable(String table) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE " + table);
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createTable(String table, Map<String, String> columns, String id) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table).append(" (");
        if (columns.isEmpty()) {
            return false;
        }

        columns.forEach((key, value) -> {
            if (Objects.equals(key, id)) {
                sql.append(key).append(" ").append(value).append(" PRIMARY KEY,");
            } else {
                sql.append(key).append(" ").append(value).append(",");
            }
        });

        char[] arr = sql.toString().toCharArray();
        new Strings().clearStringBuilder(sql);

        for (int i = 0; i < arr.length - 1; i++) {
            sql.append(arr[i]);
        }

        sql.append(");");
        System.out.println(sql);
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql.toString());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tableExists(String table) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, table, null);
        return tables.next();
    }

    @Override
    public boolean columnExists(String table, String column) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet columns = dbm.getColumns(null, null, table, column);
        return columns.next();
    }

    @Override
    public ArrayList<Object> getColumnData(String table, String column, String type) throws SQLException {
        ArrayList<Object> values = new ArrayList<>();

        String sql = "SELECT " + column + " FROM " + table;
        try (PreparedStatement prstmt = connection.prepareStatement(sql)) {

            ResultSet rs = prstmt.executeQuery();
            while (rs.next()) {

                switch (type.toLowerCase()) {
                    case "int":
                        values.add(rs.getInt(column));
                        break;

                    case "float":
                        values.add(rs.getFloat(column));
                        break;

                    case "bytes":
                        values.add(rs.getBytes(column));
                        break;

                    case "date":
                        values.add(rs.getDate(column));
                        break;

                    default:
                        values.add(rs.getString(column));
                        break;
                }
            }
        }

        return values;
    }

    @Override
    public int countRecords(String table) throws SQLException {
        int count = 0;

        Statement stmt = connection.createStatement();
        String query = "SELECT COUNT(*) FROM " + table;
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            count = rs.getInt(1);
        }

        return count;
    }

    @Override
    public void fillTable(String table, String[] columns, JTable jTable) throws SQLException {
        new AWTSwingUI().clearTable(jTable, true);

        PreparedStatement st = connection.prepareStatement(createSelectSql(table, columns));
        ResultSet rs = st.executeQuery();
        jTable.setModel(DbUtils.resultSetToTableModel(rs));
    }

    @Override
    public String createSelectSql(String table, String[] columns) {
        return null;
    }

    @Override
    public void exportTableToExcelFile(JTable table, int columns, String path) throws IOException {
        if (Objects.isNull(table) || Objects.isNull(path)) {
            return;
        }

        int rowID = 0;
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();

        TreeMap<String, Object[]> data = new TreeMap<>();

        data.put("0", new Object[]{dtm.getColumnName(0), dtm.getColumnName(1),
                dtm.getColumnName(2), dtm.getColumnName(3), dtm.getColumnName(4), dtm.getColumnName(5)});

        for (int i = 0; i < dtm.getRowCount(); i++) {
            for (int j = 0; j < dtm.getColumnCount(); j++) {
                data.put(String.valueOf(i + 1),
                        new Object[]{dtm.getValueAt(i, j).toString(), dtm.getValueAt(i, j++).toString(),
                                dtm.getValueAt(i, j++).toString(), dtm.getValueAt(i, j++).toString(),
                                dtm.getValueAt(i, j++).toString()});
            }
        }

        Set<String> ids = data.keySet();
        XSSFRow row;

        for (String key : ids) {
            row = ws.createRow(rowID++);
            Object[] values = data.get(key);

            int cellID = 0;
            for (Object o : values) {
                Cell cell = row.createCell(cellID++);
                cell.setCellValue(o.toString());
            }
        }

        if (!path.endsWith(".xlsx")) {
            path = path.concat(".xlsx");
        }

        try (FileOutputStream fos = new FileOutputStream(path)) {
            wb.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
