package com.rabbitcrud.rabbitcrud.pojo;


import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableColumn;
import com.rabbitcrud.rabbitcrud.annotation.TableID;

import java.io.Serializable;

@DBTable("t_student")
public class Student implements Serializable {

    @TableID("id")
    private Integer id;
    @TableColumn("s_name")
    private String name;
    @TableColumn("age")
    private Integer age;
    @TableColumn("s_arrdess")
    private String arrdess;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getArrdess() {
        return arrdess;
    }

    public void setArrdess(String arrdess) {
        this.arrdess = arrdess;
    }
}
