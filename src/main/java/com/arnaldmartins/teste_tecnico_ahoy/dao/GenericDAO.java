package com.arnaldmartins.teste_tecnico_ahoy.dao;

import java.util.List;
import java.io.Serializable;

public interface GenericDAO<T, ID extends Serializable> {
    T findById(ID id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(T entity);
}
