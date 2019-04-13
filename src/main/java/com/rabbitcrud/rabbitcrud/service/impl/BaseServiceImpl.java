package com.rabbitcrud.rabbitcrud.service.impl;

import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableID;
import com.rabbitcrud.rabbitcrud.dao.BaseDaoMapper;
import com.rabbitcrud.rabbitcrud.pojo.Content;
import com.rabbitcrud.rabbitcrud.service.BaseService;
import com.rabbitcrud.rabbitcrud.utils.ConditionalParameter;
import com.rabbitcrud.rabbitcrud.utils.SqlParameterConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.expression.Fields;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用业务实现
 * @author dxy
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseDaoMapper baseDaoMapper;


    /**
     * 根据条件查询数据返回list(多条记录)
     * @param conditionalParameter
     * @return
     */
    public List<Map<String, Object>> searchConditionalList(ConditionalParameter conditionalParameter) {
        Map<String,Object> conditional=SqlParameterConvert.toConvert(conditionalParameter);
        return baseDaoMapper.searchConditionalList(conditional);
    }

    /**
     * 根据条件查询数据返回map(单条记录)
     * @param conditionalParameter
     * @return
     */
    public Map<String, Object> searchConditionalMap(ConditionalParameter conditionalParameter) {
        Map<String,Object> conditional=SqlParameterConvert.toConvert(conditionalParameter);
        return baseDaoMapper.searchConditionalMap(conditional);
    }

    /**
     * 根据主键查询数据
     * @param obj
     * @param value
     * @return
     */
    public Map<String, Object> getObjectById(Class<?> obj, Object value) {
        String fieldName="";
        String tableName="";
        if(obj!=null){
            if(obj.isAnnotationPresent(DBTable.class)){
                DBTable table=obj.getAnnotation(DBTable.class);
                tableName=table.value();
                Field[] fields=obj.getDeclaredFields();
                if(fields.length>0){
                    for(int x=0;x<fields.length;x++){
                        if(fields[x].isAnnotationPresent(TableID.class)){
                            fieldName=fields[x].getAnnotation(TableID.class).value();
                            break;
                        }
                    }
                }else{
                    throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取到指定字段...");
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取TableName，获取数据错误...");
            }
        }
        return baseDaoMapper.getObjectById(tableName,fieldName,value.toString());
    }

    /**
     * 根据条件查询数据返回总记录数
     * @param conditionalParameter
     * @return
     */
    public Integer searchCount(ConditionalParameter conditionalParameter) {
        return baseDaoMapper.searchCount(SqlParameterConvert.toConvert(conditionalParameter));
    }

    /**
     * 保存数据
     * @param obj
     * @return
     */
    public Integer saveObject(Object obj) {
        Class<?> objClass=obj.getClass();
        Map<String,Object> objectMap=new HashMap<>();
        if(objClass!=null){
            StringBuffer sql=new StringBuffer();
            if(objClass.isAnnotationPresent(DBTable.class)){
                sql.append("insert into "+objClass.getAnnotation(DBTable.class).value()+"(");
                Field[] fields=objClass.getDeclaredFields();
                if(fields.length>0){
                    String temp="";
                    String temp2="";
                    for(int x=0;x<fields.length;x++){
                        Object value= null;
                        try {
                            fields[x].setAccessible(true);//通过反射访问private字段
                            value = fields[x].get(obj);//通过反射获取字段value
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if(value!=null){
                            temp+=fields[x].getName()+",";
                            if(value instanceof Integer){
                                temp2+=""+Integer.parseInt(value.toString())+",";
                            }else if(value instanceof String){
                                temp2+="'"+value.toString()+"',";
                            }else if(value instanceof Double){
                                temp2+=""+Double.parseDouble(value.toString())+"";
                            }else if(value instanceof Float){
                                temp2+=""+Float.parseFloat(value.toString())+"";
                            }else if(value instanceof Date){
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                String date=sdf.format(value);
                                temp2+="'"+date+"',";
                            }
                        }
                    }
                    temp=temp.substring(0,temp.lastIndexOf(","));
                    temp2=temp2.substring(0,temp2.lastIndexOf(","));
                    sql.append(temp+")");
                    sql.append(" value("+temp2+");");
                }else {
                    throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取到指定字段，保存数据错误...");
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取TableName，保存数据错误...");
            }
            objectMap.put("sqlContent",sql.toString());
        }
        return baseDaoMapper.saveObject(objectMap);
    }

    /**
     * 根据条件修改数据
     * @param values
     * @param conditionalParameter
     * @return
     */
    public Integer updateObject(Map<String, Object> values, ConditionalParameter conditionalParameter) {
        Map<String,Object> objectMap=new HashMap<>();
        if(values!=null){
            Map<String,String[]> columns=conditionalParameter.getColumns();
            StringBuffer sql=new StringBuffer();
            sql.append("update "+conditionalParameter.getTableName()+" t1");
            sql.append(" set");
            String temp="";
            for(String item:values.keySet()){
                 String[]  columnDes=columns.get(item);
                 Object columnValue=values.get(item);
                 if(columnDes!=null){
                     if(columnValue!=null){
                         if(columnValue instanceof String){
                             temp+="t1."+columnDes[0]+"='"+columnValue+"',";
                         }else if(columnValue instanceof Integer){
                             temp+="t1."+columnDes[0]+"="+Integer.parseInt(columnValue.toString())+",";
                         }else if(columnValue instanceof Double){
                             temp+="t1."+columnDes[0]+"="+Double.parseDouble(columnValue.toString())+",";
                         }else if(columnValue instanceof Float){
                             temp+="t1."+columnDes[0]+"="+Float.parseFloat(columnValue.toString())+",";
                         }else if(columnValue instanceof Date){
                             SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                             String date=sdf.format(columnValue);
                             temp+="t1."+columnDes[0]+"='"+date+"',";
                         }
                     }
                 }else{
                     throw new NullPointerException(">>>>>>>>>>>>>>>获取或解析bean错误，修改数据错误...");
                 }
            }
            temp=temp.substring(0,temp.lastIndexOf(","));
            sql.append(" "+temp);
            if(conditionalParameter.getWhere().toString()!=null){
                sql.append(" "+conditionalParameter.getWhere().toString());
            }
            objectMap.put("sqlContent",sql.toString());
        }else {
            throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取要修改的属性和值，修改数据错误...");
        }
        return baseDaoMapper.updateObject(objectMap);
    }

    /**
     * 根据条件删除数据
     * @param conditionalParameter
     * @return
     */
    public Integer deleteObject(ConditionalParameter conditionalParameter) {
        return baseDaoMapper.deleteObject(SqlParameterConvert.toConvert(conditionalParameter));
    }

    /**
     * 根据主键删除数据
     * @param obj
     * @param value
     * @return
     */
    public Integer deleteObjectById(Class<?> obj, Object value) {
        String fieldName="";
        String tableName="";
        if(obj!=null){
            if(obj.isAnnotationPresent(DBTable.class)){
                DBTable table=obj.getAnnotation(DBTable.class);
                tableName=table.value();
                Field[] fields=obj.getDeclaredFields();
                if(fields.length>0){
                    for(int x=0;x<fields.length;x++){
                        if(fields[x].isAnnotationPresent(TableID.class)){
                            fieldName=fields[x].getAnnotation(TableID.class).value();
                            break;
                        }
                    }
                }else{
                    throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取到指定字段...");
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>未能够获取TableName，获取数据错误...");
            }
        }
        return baseDaoMapper.deleteObjectById(tableName,fieldName,value.toString());
    }
}
