package iti.jets.app.server.db;

import java.io.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceFactory {

    private static DataSource dataSource = null;

    private DataSourceFactory() {
    }

    private static void init() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("/db.properties");
            prop.setProperty("MYSQL_DB_URL", "jdbc:mysql://localhost:3308/chat_app");
            System.out.println("here");
            prop.setProperty("MYSQL_DB_USERNAME", "root");
            prop.setProperty("MYSQL_DB_PASSWORD", "@");
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DataSource getMySQLDataSource() {
        if (dataSource != null)
            return dataSource;
        //init();
        Properties p = new Properties();
        //InputStream fin = null;
        MysqlDataSource mySqlDataSource = null;
        try {

            //fin = new FileInputStream("/db.properties");
            //p.load(fin);
            mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setURL("jdbc:mysql://127.0.0.1:3308/chat_app");
            mySqlDataSource.setUser("root");
            mySqlDataSource.setPassword(p.getProperty(""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataSource = mySqlDataSource;
    }
}