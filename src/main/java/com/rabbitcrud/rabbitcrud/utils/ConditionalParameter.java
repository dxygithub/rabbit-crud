package com.rabbitcrud.rabbitcrud.utils;

import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableColumn;
import com.rabbitcrud.rabbitcrud.annotation.TableID;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 条件参数类，主要用于sql条件,目前只适应Mysql数据库，其他数据库还暂时未适应
 * @author dxy
 * 2018-12-31
 */
public class ConditionalParameter implements Serializable {
    //select * from student where t1.age=22 and t1.class='y2' group by t1.sex,t1.age,t1.class order by t1.createDate desc;
    private String tableName;//数据库表名
    private StringBuffer fields=new StringBuffer();//sql查询返回的字段
    private StringBuffer where=new StringBuffer();//where条件
    private StringBuffer orderBy=new StringBuffer();//orderBy条件
    private StringBuffer leftJoin=new StringBuffer();
    private StringBuffer rightJoin=new StringBuffer();
    //字段集合,key是bean的属性名称，value是字段数组，其中[0]是数据库表中实际字段名称,[1]是bean中字段的数据类型
    private Map<String,String[]> columns=new HashMap<String,String[]>();
    public ConditionalParameter(Class<?> obj){
        if(obj!=null){
            if(obj.isAnnotationPresent(DBTable.class)){
                DBTable dbTable=obj.getAnnotation(DBTable.class);
                String dbName=dbTable.value();
                if(dbName!=null&&!"".equals(dbName)){
                    this.tableName=dbName;//获取数据库对应的表名称
                    this.where.append(" where 1=1");
                }
            }else{
                throw new NullPointerException(">>>>>>>>>>>>>>>获取DBTableName错误...");
            }
            Field[] fields=obj.getDeclaredFields();//获取实体类所有字段
            if(fields.length>0){
                for(int x=0;x<fields.length;x++){
                    if(fields[x].isAnnotationPresent(TableColumn.class)){
                        TableColumn tableColumn=fields[x].getAnnotation(TableColumn.class);
                        String columnName=tableColumn.value();
                        String columnType=fields[x].getType().toString();
                        if(columnName!=null&&!"".equals(columnName)){
                            if(columnType!=null&&!"".equals(columnType)){
                                String[] properties={columnName,columnType};
                                columns.put(fields[x].getName(),properties);
                            }
                        }
                    }else if(fields[x].isAnnotationPresent(TableID.class)){
                        TableID tableColumn=fields[x].getAnnotation(TableID.class);
                        String columnName=tableColumn.value();
                        String columnType=fields[x].getType().toString();
                        if(columnName!=null&&!"".equals(columnName)){
                            if(columnType!=null&&!"".equals(columnType)){
                                String[] properties={columnName,columnType};
                                columns.put(fields[x].getName(),properties);
                            }
                        }
                    }
                }
            }
        }
    }

    public ConditionalParameter(){}

    /**
     * 设置数据库表名称
     * 格式：如 Student.class
     * @param obj
     */
    public void setTableName(Class<?> obj){
        if(obj!=null){
            if(obj.isAnnotationPresent(DBTable.class)){
                DBTable dbTable=obj.getAnnotation(DBTable.class);
                String dbName=dbTable.value();
                if(dbName!=null&&!"".equals(dbName)){
                    this.tableName=dbName;//获取数据库对应的表名称
                    this.where.append("where 1=1");
                    this.orderBy.append("order by");
                    this.leftJoin.append(this.tableName+" t1 left join");
                    this.rightJoin.append(this.tableName+" t1 right join");
                }
            }else{
                throw new NullPointerException(">>>>>>>>>>>>>>>获取DBTableName错误...");
            }
            Field[] fields=obj.getDeclaredFields();//获取实体类所有字段
            if(fields.length>0){
                for(int x=0;x<fields.length;x++){
                    if(fields[x].isAnnotationPresent(TableColumn.class)){
                        TableColumn tableColumn=fields[x].getAnnotation(TableColumn.class);
                        String columnName=tableColumn.value();
                        String columnType=fields[x].getType().toString();
                        if(columnName!=null&&!"".equals(columnName)){
                            if(columnType!=null&&!"".equals(columnType)){
                                String[] properties={columnName,columnType};
                                columns.put(fields[x].getName(),properties);
                            }
                        }
                    }else if(fields[x].isAnnotationPresent(TableID.class)){
                        TableID tableColumn=fields[x].getAnnotation(TableID.class);
                        String columnName=tableColumn.value();
                        String columnType=fields[x].getType().toString();
                        if(columnName!=null&&!"".equals(columnName)){
                            if(columnType!=null&&!"".equals(columnType)){
                                String[] properties={columnName,columnType};
                                columns.put(fields[x].getName(),properties);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置等值条件
     * 格式：如 name=张三 and age=22
     * 如果条件是"Date"类型，目前只匹配到yyyy-MM-dd，还未匹配到hh:mm:ss
     * @param properties 实体类中的属性名称，如果是leftJoin或rightJoin查询时，请填写实际数据表中的字段名称,如：t1.name
     * @param value 值
     */
    public void whereEqualTo(String properties,Object value){
        if(properties!=null&&!"".equals(properties)){
            if(properties.indexOf(".")!=-1){
                if(value!=null&&!"".equals(value)){
                    if(value instanceof String){
                        this.where.append(" and "+properties+"='"+value.toString()+"'");
                    }else if(value instanceof Integer){
                        this.where.append(" and "+properties+"="+(Integer)value);
                    }else if(value instanceof Double){
                        this.where.append(" and "+properties+"="+(Double)value);
                    }else if(value instanceof Float){
                        this.where.append(" and "+properties+"="+(Float)value);
                    }else if(value instanceof Date){
                        String tempVal="";
                        SimpleDateFormat slf=null;
                        if(value instanceof Date){
                            slf=new SimpleDateFormat("yyyy-MM-dd");
                            tempVal=slf.format(value);
                        }else {
                            tempVal=value.toString();
                            if(tempVal.indexOf("/")!=-1){
                                slf=new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date=slf.parse(tempVal);
                                    tempVal=slf.format(date);
                                } catch (ParseException e) {
                                    System.out.println(">>>>>>>>>>>>>>>日期参数:"+properties+" >>转换错误...");
                                    e.printStackTrace();
                                }
                            }
                        }
                        this.where.append(" and DATE("+properties+")=STR_TO_DATE('"+tempVal+"','%Y-%m-%d')");
                    }
                }
            }else{
                String[] propValue=columns.get(properties);
                if(value!=null&&!"".equals(value)){
                    if(propValue!=null&&propValue.length>0){
                        if(propValue[1].endsWith("String")){
                            this.where.append(" and t1."+propValue[0]+"='"+value.toString()+"'");
                        }else if(propValue[1].endsWith("Integer")){
                            this.where.append(" and t1."+propValue[0]+"="+Integer.parseInt(value.toString()));
                        }else if(propValue[1].endsWith("Date")){
                            String tempVal="";
                            SimpleDateFormat slf=null;
                            if(value instanceof Date){
                                slf=new SimpleDateFormat("yyyy-MM-dd");
                                tempVal=slf.format(value);
                            }else {
                                tempVal=value.toString();
                                if(tempVal.indexOf("/")!=-1){
                                    slf=new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        Date date=slf.parse(tempVal);
                                        tempVal=slf.format(date);
                                    } catch (ParseException e) {
                                        System.out.println(">>>>>>>>>>>>>>>日期参数:"+properties+" >>转换错误...");
                                        e.printStackTrace();
                                    }
                                }
                            }
                            this.where.append(" and DATE(t1."+propValue[0]+")=STR_TO_DATE('"+tempVal+"','%Y-%m-%d')");
                        }else if(propValue[1].endsWith("Double")){
                            this.where.append(" and t1."+propValue[0]+"="+Double.parseDouble(value.toString()));
                        }else if(propValue[1].endsWith("Float")){
                            this.where.append(" and t1."+propValue[0]+"="+Float.parseFloat(value.toString()));
                        }
                    }
                }
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置模糊条件
     * 格式：如 name like 'abc%'或name like '%abc%'或name like '%abc'
     * @param properties 实体类中的属性名称
     * @param value 值
     */
    public void like(String properties,Object value){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                if(value instanceof Date){
                    SimpleDateFormat slf=new SimpleDateFormat("yyyy-MM-dd");
                    String tempVal=slf.format(value);
                    if(value.toString().contains("%")){
                        this.where.append(" and t1."+propValue[0]+" like '"+value.toString()+"'");
                    }else{
                        this.where.append(" and t1."+propValue[0]+" like concat('%','"+tempVal+"','%')");
                    }
                }else{
                    if(value.toString().contains("%")){
                        this.where.append(" and t1."+propValue[0]+" like '"+value.toString()+"'");
                    }else{
                        this.where.append(" and t1."+propValue[0]+" like concat('%','"+value.toString()+"','%')");
                    }
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>获取TableColumn错误...");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段等于null
     * 格式：如 name is null
     * @param properties 实体类中的属性名称
     */
    public void isNull(String properties){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                this.where.append(" and t1."+propValue[0]+" is null");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段不等于null
     * 格式：如 name is not null
     * @param properties 实体类中的属性名称
     */
    public void isNotNull(String properties){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                this.where.append(" and t1."+propValue[0]+" is not null");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段等于null或者等于""
     * 格式：如 name is null or name=''
     * @param properties 实体类中的属性名称
     */
    public void isNullOrEqual(String properties){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                this.where.append(" and t1."+propValue[0]+" is null or t1."+propValue[0]+"=''");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段不等于null或者不等于""
     * 格式：如 name is not null or name<>''
     * @param properties 实体类中的属性名称
     */
    public void isNotNullOrEqual(String properties){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                this.where.append(" and t1."+propValue[0]+" is not null and t1."+propValue[0]+"<>''");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段不等于某个条件
     * 格式：如 age<>22 或 color<>'red'
     * @param properties 实体类中的属性名称，如果是leftJoin或rightJoin查询时，请填写实际数据表中的字段名称,如：t1.name
     * @param value 值
     */
    public void notEqual(String properties,Object value){
        if(properties!=null&&!"".equals(properties)){
            if(properties.indexOf(".")!=-1){
                if(value!=null&&!"".equals(value)){
                    if(value instanceof String){
                        this.where.append(" and "+properties+"<>'"+value.toString()+"'");
                    }else if(value instanceof Integer){
                        this.where.append(" and "+properties+"<>"+(Integer)value);
                    }else if(value instanceof Double){
                        this.where.append(" and "+properties+"<>"+(Double)value);
                    }else if(value instanceof Float){
                        this.where.append(" and "+properties+"<>"+(Float)value);
                    }else if(value instanceof Date){
                        String tempVal="";
                        SimpleDateFormat slf=null;
                        if(value instanceof Date){
                            slf=new SimpleDateFormat("yyyy-MM-dd");
                            tempVal=slf.format(value);
                        }else {
                            tempVal=value.toString();
                            if(tempVal.indexOf("/")!=-1){
                                slf=new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date=slf.parse(tempVal);
                                    tempVal=slf.format(date);
                                } catch (ParseException e) {
                                    System.out.println(">>>>>>>>>>>>>>>日期参数:"+properties+" >>转换错误...");
                                    e.printStackTrace();
                                }
                            }
                        }
                        this.where.append(" and DATE("+properties+")<>STR_TO_DATE('"+tempVal+"','%Y-%m-%d')");
                    }
                }
            }else{
                String[] propValue=columns.get(properties);
                if(value!=null&&!"".equals(value)){
                    if(propValue!=null&&propValue.length>0){
                        if(propValue[1].endsWith("String")){
                            this.where.append(" and t1."+propValue[0]+"<>'"+value.toString()+"'");
                        }else if(propValue[1].endsWith("Integer")){
                            this.where.append(" and t1."+propValue[0]+"<>"+Integer.parseInt(value.toString()));
                        }else if(propValue[1].endsWith("Date")){
                            String tempVal="";
                            SimpleDateFormat slf=null;
                            if(value instanceof Date){
                                slf=new SimpleDateFormat("yyyy-MM-dd");
                                tempVal=slf.format(value);
                            }else {
                                tempVal=value.toString();
                                if(tempVal.indexOf("/")!=-1){
                                    slf=new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        Date date=slf.parse(tempVal);
                                        tempVal=slf.format(date);
                                    } catch (ParseException e) {
                                        System.out.println(">>>>>>>>>>>>>>>日期参数:"+properties+" >>转换错误...");
                                        e.printStackTrace();
                                    }
                                }
                            }
                            this.where.append(" and DATE(t1."+propValue[0]+")<>STR_TO_DATE('"+tempVal+"','%Y-%m-%d')");
                        }else if(propValue[1].endsWith("Double")){
                            this.where.append(" and t1."+propValue[0]+"<>"+Double.parseDouble(value.toString()));
                        }else if(propValue[1].endsWith("Float")){
                            this.where.append(" and t1."+propValue[0]+"<>"+Float.parseFloat(value.toString()));
                        }
                    }
                }
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段等于多个值
     * 格式：如 color in('red','blue','black','yellow')或age in(22,23,24,25,26)
     * @param properties 实体类中的属性名称
     * @param obj value[]
     */
    public void in(String properties,Object[] obj){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                String temp="";
                if(obj instanceof String[]){
                    for(String item:(String[]) obj){
                       temp+="'"+item+"',";
                   }
                }else{
                    for(Object item:obj){
                        temp+=""+item+",";
                    }
                }
                if(!"".equals(temp)){
                    temp=temp.substring(0,temp.lastIndexOf(","));
                    this.where.append(" and t1."+propValue[0]+" in("+temp+")");
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>获取TableColumn错误...");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置字段不等于多个值
     * 格式：如 color not in('red','blue','black','yellow')或age not in(22,23,24,25,26)
     * @param properties 实体类中的属性名称
     * @param obj value[]
     */
    public void notIn(String properties,Object[] obj){
        if(properties!=null&&!"".equals(properties)){
            String[] propValue=columns.get(properties);
            if(propValue!=null&&propValue.length>0){
                String temp="";
                if(obj instanceof String[]){
                    for(String item:(String[]) obj){
                        temp+="'"+item+"',";
                    }
                }else{
                    for(Object item:obj){
                        temp+=""+item+",";
                    }
                }
                if(!"".equals(temp)){
                    temp=temp.substring(0,temp.lastIndexOf(","));
                    this.where.append(" and t1."+propValue[0]+" not in("+temp+")");
                }
            }else {
                throw new NullPointerException(">>>>>>>>>>>>>>>获取TableColumn错误...");
            }
        }else{
            throw new NullPointerException(">>>>>>>>>>>>>>>获取properties错误...");
        }
    }

    /**
     * 设置左外连接查询
     * 格式：如 select * from content t1	 LEFT JOIN type t2 on t1.id=t2.typeNo
     * @param obj 实体类==需要连接的数据表,如：Student.class
     * @param on 连接条件，如：t1.id=t2.typeNo,注意：此处字段名称是表中实际的字段名称
     */
    public void leftJoin(Class<?> obj,String on){
        this.rightJoin.setLength(0);
        if(obj!=null){
            DBTable dbTable=obj.getAnnotation(DBTable.class);
            String dbName=dbTable.value();
            String tableName=dbName;//获取数据库对应的表名称
            if(tableName!=null&&!"".equals(tableName)){
                if(on!=null&&!"".equals(on)){
                    this.leftJoin.append(" left join "+tableName+" t2 on "+on);
                }else {
                    throw new NullPointerException(">>>>>>>>>>>>>>>获取连接条件错误...");
                }
            }else{
                throw new NullPointerException(">>>>>>>>>>>>>>>获取tableName错误...");
            }
        }
    }

    /**
     * 设置右外连接查询
     * 格式：如 select * from content t1	 Right JOIN type t2 on t1.id=t2.typeNo
     * @param obj 实体类==需要连接的数据表,如：Student.class
     * @param on 连接条件，如：t1.id=t2.typeNo,注意：此处字段名称是表中实际的字段名称
     */
    public void rightJoin(Class<?> obj,String on){
        if(obj!=null){
            DBTable dbTable=obj.getAnnotation(DBTable.class);
            String dbName=dbTable.value();
            String tableName=dbName;//获取数据库对应的表名称
            if(tableName!=null&&!"".equals(tableName)){
                if(on!=null&&!"".equals(on)){
                    this.rightJoin.append(" right join "+tableName+" t2 on "+on);
                }else{
                    throw new NullPointerException(">>>>>>>>>>>>>>>获取连接条件错误...");
                }
            }else{
                throw new NullPointerException(">>>>>>>>>>>>>>>获取tableName错误...");
            }
        }
    }

    /**
     * 设置返回结果集中的字段
     * 格式：如 'name,age,sex,address'，以逗号隔开，如果是leftJoin或rightJoin查询时，请以此格式设置返回的字段：'t1.name,t1.age,t2.sex,t2.address'
     * @param fields 字段名称
     */
    public void setReturnFields(String fields){
        if(fields!=null&&!"".equals(fields)){
            if(fields.contains(".")){
                this.fields.append(fields);
            }else{
                String[] columns=fields.split(",");
                String tempFields="";
                for(int x=0;x<columns.length;x++){
                    try {
                        String[] temp=this.columns.get(columns[x]);
                        tempFields+="t1."+temp[0]+",";
                    } catch (Exception e) {
                        throw new NullPointerException(">>>>>>>>>>>>>>>字段或属性错误...");
                    }
                }
                if(!"".equals(tempFields)){
                    tempFields=tempFields.substring(0,tempFields.lastIndexOf(","));
                    this.fields.append(tempFields);
                }else {
                    throw new NullPointerException(">>>>>>>>>>>>>>>设置返回结果集中的字段:"+fields+">>错误...");
                }
            }
        }
    }

    /**
     * 设置结果集排序
     * 格式：如 order by age asc 或 order by age desc
     * @param fields 字段名称,可设置单个或多个，多个使用逗号隔开,如果是leftJoin或rightJoin查询时，请以此格式设置排序的字段：'t1.name,t1.age,t2.sex,t2.address'
     * @param type 排序类型：升序排序设置为:asc，降序排序设置为:desc
     */
    public void orderBy(String fields,String type){
        if(fields!=null&&!"".equals(fields)){
            if(type!=null&&!"".equals(type)){
                if(fields.contains(".")){
                    this.orderBy.append(" order by "+fields+" "+type);
                }else{
                    String[] columns=fields.split(",");
                    String tempFields="";
                    for(int x=0;x<columns.length;x++){
                        try {
                            String[] temp=this.columns.get(columns[x]);
                            tempFields+="t1."+temp[0]+",";
                        } catch (Exception e) {
                            throw new NullPointerException(">>>>>>>>>>>>>>>字段或属性错误...");
                        }
                    }
                    if(!"".equals(tempFields)){
                        tempFields=tempFields.substring(0,tempFields.lastIndexOf(","));
                        this.orderBy.append(" order by "+tempFields+" "+type);
                    }else {
                        throw new NullPointerException(">>>>>>>>>>>>>>>设置结果集排序中的字段:"+fields+">>错误...");
                    }
                }
            }
        }
    }


    public String getTableName() {
        return tableName;
    }

    public StringBuffer getFields() {
        return fields;
    }

    public StringBuffer getWhere() {
        return where;
    }

    public StringBuffer getOrderBy() {
        return orderBy;
    }

    public StringBuffer getLeftJoin() {
        return leftJoin;
    }

    public StringBuffer getRightJoin() {
        return rightJoin;
    }

    public Map<String, String[]> getColumns() {
        return columns;
    }

    /*public void setColumns(Map<String, String[]> columns) {
        this.columns = columns;
    }*/
}
