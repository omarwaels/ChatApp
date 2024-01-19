package iti.jets.app.server.db;

import java.io.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DataSourceFactory {
    INSTANCE;  // The single instance

    private static DataSource dataSource;


     DataSourceFactory() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        Properties p = new Properties();
        MysqlDataSource mySqlDataSource = null;

        try {
            mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setURL("jdbc:mysql://127.0.0.1:3308/chat_app");
            mySqlDataSource.setUser("root");
            mySqlDataSource.setPassword(p.getProperty(""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        dataSource = mySqlDataSource;
    }


    public static DataSource getMySQLDataSource() {
        return INSTANCE.dataSource;
    }
}
