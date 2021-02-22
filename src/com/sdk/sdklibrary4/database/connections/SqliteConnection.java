package com.sdk.sdklibrary4.database.connections;

import com.sdk.sdklibrary4.storage.FileSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private String path;
    private String databaseName;
    private Connection connection;

    public SqliteConnection(String path) {
        this.path = path;
        this.databaseName = new FileSystem(path).getName();
        this.connect();
    }

    public boolean connect() {
        try {
            String path = null;
            if (!new FileSystem(path).exist()) {
                return false;
            } else {
                Class.forName("org.sqlite.JDBC");
                path = "jdbc:sqlite:".concat(this.path);
                this.connection = DriverManager.getConnection(path);
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SqliteConnection setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getPath() {
        return this.path;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
}
