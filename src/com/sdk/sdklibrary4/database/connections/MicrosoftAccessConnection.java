package com.sdk.sdklibrary4.database.connections;

import com.sdk.sdklibrary4.storage.FileSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MicrosoftAccessConnection {
    private String path;
    private String databaseName;
    private Connection connection;

    public MicrosoftAccessConnection(String path) {
        this.path = path;
        this.databaseName = new FileSystem(path).getName();
        this.connect();
    }

    public boolean connect() {
        try {
            if (!new FileSystem(path).exist()) {
                return false;
            } else {
                this.path = "jdbc:ucanaccess://".concat(this.path);
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                this.connection = DriverManager.getConnection(this.path);
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MicrosoftAccessConnection setConnection(Connection connection) {
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
