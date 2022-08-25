package org.java.dao;

import org.java.dbUtils.SearchSpecification;

import java.util.List;

public interface Dao<T> {
    T get(Number id);

    List<T> getAll();

    List<T> getAll(SearchSpecification spec);

    void update(T t);

    void delete(T t);

    void insert(T t);
}
