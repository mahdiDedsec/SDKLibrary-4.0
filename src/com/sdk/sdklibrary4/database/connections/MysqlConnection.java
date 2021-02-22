package com.sdk.sdklibrary4.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {

    private Connection connection;
    private String connectionStr;
    private String databaseName;
    private String ip;
    private String username;
    private String password;
    private String port;

    public MysqlConnection(String ip, String port, String username, String password, String databaseName) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.connect();
    }

    public MysqlConnection(String databaseName) {
        this.ip = "localhost";
        this.port = "3306";
        this.username = "root";
        this.password = "";
        this.databaseName = databaseName;
        this.connect();
    }

    public boolean connect() {
        try {
            this.connectionStr = "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.databaseName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.connectionStr, this.username, this.password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public MysqlConnection setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public String getConnectionStr() {
        return this.connectionStr;
    }

    public String getIp() {
        return this.ip;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPort() {
        return this.port;
    }
}
