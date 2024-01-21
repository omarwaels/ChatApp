package iti.jets.app.server.db;

public interface Dao<T, U> {
    T getById(U u);

    int insert(T t);

    int update(T t);

    int delete(U u);
}
