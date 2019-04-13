package com.rabbitcrud.rabbitcrud.pojo;

import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableColumn;
import com.rabbitcrud.rabbitcrud.annotation.TableID;

import java.io.Serializable;
import java.util.Date;

@DBTable("type")
public class Type implements Serializable {

    @TableID("typeid")
    private Integer typeId;

    @TableColumn("typeNo")
    private String typeNo;

    @TableColumn("typeName")
    private String typeName;

    @TableColumn("typeValNo")
    private Integer typeValNo;

    @TableColumn("typeValName")
    private String typeValName;

    @TableColumn("createName")
    private String createName;

    @TableColumn("createTime")
    private Date createTime;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeValNo() {
        return typeValNo;
    }

    public void setTypeValNo(Integer typeValNo) {
        this.typeValNo = typeValNo;
    }

    public String getTypeValName() {
        return typeValName;
    }

    public void setTypeValName(String typeValName) {
        this.typeValName = typeValName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
