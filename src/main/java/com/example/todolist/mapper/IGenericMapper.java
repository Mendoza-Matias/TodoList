package com.example.todolist.mapper;

/*
 *   T entity
 *   Y dto
 */

import java.util.List;
import java.util.stream.Collectors;

public abstract class IGenericMapper<T, Y> {

    public IGenericMapper() {
    }

    public abstract Y map(T entity);

    public abstract T unmap(Y dto);

    public List<Y> mapList(List<T> entityList) {
        return entityList.stream().map(et -> this.map(et)).collect(Collectors.toList());
    }

    public List<T> unMapList(List<Y> dtoList) {
        return dtoList.stream().map(et -> this.unmap(et)).collect(Collectors.toList());
    }
}
