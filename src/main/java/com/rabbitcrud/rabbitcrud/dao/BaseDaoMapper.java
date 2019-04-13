package com.rabbitcrud.rabbitcrud.dao;

import com.rabbitcrud.rabbitcrud.utils.ConditionalParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据层公用Dao接口
 * @author dxy
 */
public interface BaseDaoMapper {

    /**
     * 根据条件参数返回多个数据Map集合
     * @param conditionalParameter
     * @return
     */
    List<Map<String,Object>> searchConditionalList(Map<String,Object> conditionalParameter);

    /**
     * 根据条件参数返回单个数据Map
     * @param conditionalParameter
     * @return
     */
    Map<String,Object> searchConditionalMap(Map<String,Object> conditionalParameter);

    /**
     * 根据主键返回指定表的单个数据Map
     * @param tableName 表名称
     * @param  primaryKey 主键
     * @param value 值
     * @return
     */
    Map<String,Object> getObjectById(@Param("tableName") String tableName,@Param("primaryKey") String primaryKey,@Param("value") String value);

    /**
     * 根据条件参数返回数据行总数
     * @param conditionalParameter
     * @return
     */
    Integer searchCount(Map<String,Object> conditionalParameter);

    /**
     * 保存数据
     * @param obj
     * @return
     */
    Integer saveObject(Map<String,Object> obj);

    /**
     * 根据条件编辑数据
     * @param obj
     * @return
     */
    Integer updateObject(Map<String,Object> obj);

    /**
     * 根据条件删除数据
     * @param conditionalParameter
     * @return
     */
    Integer deleteObject(Map<String,Object> conditionalParameter);

    /**
     * 根据主键删除指定表的单个记录
     * @param tableName 表名称
     * @param  primaryKey 主键
     * @param value 值
     * @return
     */
    Integer deleteObjectById(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey,@Param("value") String value);


}
