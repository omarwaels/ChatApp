package iti.jets.app.server.db;

import java.sql.ResultSet;

public interface Dao<T, U> {
    ResultSet select(U u);

    int insert(T t);

    int update(T t);

    int delete(U u);
}
