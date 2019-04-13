package com.rabbitcrud.rabbitcrud.pojo;

import com.rabbitcrud.rabbitcrud.annotation.DBTable;
import com.rabbitcrud.rabbitcrud.annotation.TableColumn;
import com.rabbitcrud.rabbitcrud.annotation.TableID;

import java.io.Serializable;
import java.util.Date;

@DBTable("content")
public class Content implements Serializable {

    @TableID("id")
    private Integer id;

    @TableColumn("source")
    private String source;

    @TableColumn("releasedate")
    private Date releaseDate;

    @TableColumn("title")
    private String title;

    @TableColumn("content")
    private String content;

    @TableColumn("imgSrc")
    private String imgSrc;

    @TableColumn("quantity")
    private Integer quantity;

    @TableColumn("typeNo")
    private String typeNo;

    @TableColumn("typeID")
    private Integer typeID;

    @TableColumn("createName")
    private String createName;

    @TableColumn("createTime")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
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
