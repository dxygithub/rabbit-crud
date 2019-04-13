package com.rabbitcrud.rabbitcrud.pojo;

import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableColumn;
import com.rabbitcrud.rabbitcrud.annotation.TableID;

import java.io.Serializable;

@DBTable("t_subject")
public class Subject implements Serializable {

    @TableID("id")
    private Integer id;
    @TableColumn("sub_name")
    private String subName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
