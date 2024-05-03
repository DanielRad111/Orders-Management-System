package dao;

import java.util.List;

public interface DAO<T>{
    T findById(int id);
    T findByName(String name);
    List<T> findAll();
    int insert(T t);
    void update(T t);
    void delete(int id);
}
