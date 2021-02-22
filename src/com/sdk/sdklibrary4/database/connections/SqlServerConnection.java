package com.sdk.sdklibrary4.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerConnection {
    private String connectionStr;

    private String host;
    private String instance;
    private String databaseName;
    private String username;
    private String password;

    private int port;
    private Connection connection;

    public SqlServerConnection(String host, String instance, String databaseName, String username, String password, int port) {
        this.host = host;
        this.instance = instance;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.port = port;

        connect();
    }

    public boolean connect() {
        try {
            connectionStr = "jdbc:sqlserver://" + host + "\\" + instance + ":" + port + ";database="+databaseName;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

            this.connection = DriverManager.getConnection(connectionStr, username, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SqlServerConnection setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public String getConnectionStr() {
        return connectionStr;
    }

    public String getHost() {
        return host;
    }

    public String getInstance() {
        return instance;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public Connection getConnection() {
        return connection;
    }
}


/*
      String host = "164.138.17.194";
        String instanceName = "DESKTOP-KO506RH";
        int port = 1433;
        String databaseName = "ziteb_life_Db";
        String username = "zitebuser";
        String password = "?Mnl1347*?";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        String url ="jdbc:sqlserver://" + host + "\\" + instanceName + ":" + port + ";database="+databaseName;


        Connection connection = DriverManager.getConnection(url, username, password);

        if (!Objects.isNull(connection)) {
            System.out.println("connected");
        }
 */