package com.rabbitcrud.rabbitcrud.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL参数转换
 */
public class SqlParameterConvert {

    /**
     * 将ConditionalParameter条件参数类转换为Map集合
     * @param conditionalParameter 条件参数类
     * @return
     */
    public static Map<String,Object> toConvert(ConditionalParameter conditionalParameter){
        Map<String,Object> conditional=new HashMap<String,Object>();
        if(conditionalParameter!=null){
            conditional.put("tableName",conditionalParameter.getTableName());
            conditional.put("where",conditionalParameter.getWhere().toString());
            conditional.put("fields",conditionalParameter.getFields().toString());
            conditional.put("leftJoin",conditionalParameter.getLeftJoin().toString());
            conditional.put("rightJoin",conditionalParameter.getRightJoin().toString());
            conditional.put("orderBy",conditionalParameter.getOrderBy().toString());
        }else {
            throw new NullPointerException(">>>>>>>>>>>>>>>ConditionalParameter转换错误...");
        }
        return conditional;
    }
}
