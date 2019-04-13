package com.rabbitcrud.rabbitcrud;

import com.rabbitcrud.rabbitcrud.pojo.Content;
import com.rabbitcrud.rabbitcrud.pojo.Student;
import com.rabbitcrud.rabbitcrud.pojo.Subject;
import com.rabbitcrud.rabbitcrud.pojo.Type;
import com.rabbitcrud.rabbitcrud.service.BaseService;
import com.rabbitcrud.rabbitcrud.utils.ConditionalParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitCrudApplicationTests {

    @Autowired
    private BaseService baseService;

    @Test
    public void contextLoads() {
        //多条件查询
        //ConditionalParameter conditionalParameter=new ConditionalParameter(Type.class);
        //conditionalParameter.whereEqualTo("releaseDate","2017-05-19");
        //conditionalParameter.like("source","%网");
        //conditionalParameter.in("id",new Integer[]{93,96,97,99,102});
        //conditionalParameter.isNullOrEqual("imgSrc");
        //conditionalParameter.notEqual("releaseDate","2018-5-14");
        //conditionalParameter.notIn("id",new Integer[]{93,96,97,99,102});
        /*conditionalParameter.leftJoin(Content.class,"t1.typeNo=t2.typeNo");
        conditionalParameter.whereEqualTo("t1.typeNo","IKLIFE");
        conditionalParameter.whereEqualTo("t1.typeValNo",1002);
        conditionalParameter.setReturnFields("t2.*");*/
        /*conditionalParameter.orderBy("createTime","desc");
        List<Map<String,Object>> data=baseService.searchConditionalList(conditionalParameter);
        System.err.println("----------------------------------------");
        for(Map<String,Object> item:data){
            System.out.println(item.get("typeid").toString()+"--"+item.get("typeNo").toString()+"--"+item.get("typeName")+"--"+item.get("createTime"));
        }
        System.err.println("----------------------------------------");*/

        //保存数据测试
        /*Type tp=new Type();
        tp.setTypeNo("IKLIFE");
        tp.setTypeName("生活圈");
        tp.setTypeValNo(1006);
        tp.setTypeValName("测试6");
        tp.setCreateName("admin");
        tp.setCreateTime(new Date());
        Integer num=baseService.saveObject(tp);
        if(num>0){
            System.out.println("保存成功");
        }else {
            System.out.println("保存失败");
        }*/

       //修改数据测试
        /*Map<String,Object> value=new HashMap<>();
        value.put("typeValName","修改测试");
        value.put("createName","dxy");
        value.put("createTime",new Date());
        ConditionalParameter con=new ConditionalParameter(Type.class);
        con.whereEqualTo("typeId",2);
        con.whereEqualTo("typeName","生活圈");
        Integer num=baseService.updateObject(value,con);
        if(num>0){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }*/

        //查询单条数据测试
        /*ConditionalParameter con=new ConditionalParameter(Type.class);
        con.whereEqualTo("typeName","生活圈");
        con.whereEqualTo("typeValNo",1001);
        Map<String,Object> value=baseService.searchConditionalMap(con);
        System.out.println("=================================");
        System.out.println(value.get("typeid").toString()+"--"+value.get("typeNo").toString()+"--"+value.get("typeName")+"--"+value.get("createTime"));
        System.out.println("=================================");*/

        //按照主键查询数据
        /*Map<String,Object> value=baseService.getObjectById(Type.class,1);
        System.out.println("=================================");
        System.out.println(value.get("typeid").toString()+"--"+value.get("typeNo").toString()+"--"+value.get("typeName")+"--"+value.get("createTime"));
        System.out.println("=================================");*/

        //删除数据测试
        /*ConditionalParameter con=new ConditionalParameter(Type.class);
        //con.whereEqualTo("typeId",18);
        con.like("typeValName","测试");
        Integer num=baseService.deleteObject(con);
        if(num>0){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }*/

        //按照主键删除数据测试
       /* Integer num=baseService.deleteObjectById(Type.class,22);
        if(num>0){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }*/
    }

    public static void main(String[] args) {
        System.out.println("duxiaoyu");
        System.out.println("wangtao");
        System.out.println("王涛");
        System.out.println("杜晓宇");
    }

}

