package iti.jets.app.server.db;

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

        BasicDataSource bDataSource = new BasicDataSource();
        bDataSource.setUrl(properties.getProperty("jdbc.url"));
        bDataSource.setUsername(properties.getProperty("jdbc.username"));
        bDataSource.setPassword(properties.getProperty("jdbc.password"));

        bDataSource.setMaxTotal(500);
        bDataSource.setMaxIdle(10);
        bDataSource.setMaxWaitMillis(60000);

        dataSource = bDataSource;
    }

    public static DataSource getMySQLDataSource() {
        return INSTANCE.dataSource;
    }
}
