package iti.jets.app.server.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum DataSourceFactory {
    INSTANCE;

    private DataSource dataSource;

    DataSourceFactory() {
        initDataSource();
    }

    private void initDataSource() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(properties.getProperty("jdbc.url"));
        mysqlDataSource.setUser(properties.getProperty("jdbc.username"));
        mysqlDataSource.setPassword(properties.getProperty("jdbc.password"));
        dataSource = mysqlDataSource;
    }

    public static DataSource getMySQLDataSource() {
        return INSTANCE.dataSource;
    }
}
