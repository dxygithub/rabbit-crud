package com.rabbitcrud.rabbitcrud.service;

import com.rabbitcrud.rabbitcrud.utils.ConditionalParameter;

import java.util.List;
import java.util.Map;

/**
 * 公用service接口
 * @author dxy
 */
public interface BaseService {


    List<Map<String,Object>> searchConditionalList(ConditionalParameter conditionalParameter);

    Map<String,Object> searchConditionalMap(ConditionalParameter conditionalParameter);

    Map<String,Object> getObjectById(Class<?> obj,Object value);

    Integer searchCount(ConditionalParameter conditionalParameter);

    Integer saveObject(Object obj);

    Integer updateObject(Map<String,Object> values,ConditionalParameter conditionalParameter);

    Integer deleteObject(ConditionalParameter conditionalParameter);

    Integer deleteObjectById(Class<?> obj,Object value);
}
