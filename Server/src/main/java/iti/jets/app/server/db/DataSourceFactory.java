package iti.jets.app.server.db;

import java.io.*;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceFactory {

    private static DataSource dataSource;

    public DataSourceFactory() {
    }

    private static void init() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("/db.properties");
            prop.setProperty("MYSQL_DB_URL", "jdbc:mysql://localhost:3306/chat_app");
            System.out.println("here");
            prop.setProperty("MYSQL_DB_USERNAME", "root");
            prop.setProperty("MYSQL_DB_PASSWORD", "Dola123@");
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
        if (dataSource != null) {
            return dataSource;
        }
        init();
        Properties p = new Properties();
        InputStream fin = null;
        MysqlDataSource mySqlDataSource = null;
        try {
            fin = new FileInputStream("/db.properties");
            p.load(fin);
            mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setURL(p.getProperty("MYSQL_DB_URL"));
            mySqlDataSource.setUser(p.getProperty("MYSQL_DB_USERNAME"));
            mySqlDataSource.setPassword(p.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        dataSource = mySqlDataSource;
        return dataSource;
    }
}
