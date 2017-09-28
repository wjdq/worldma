package com.hong.worldma.entity.wm;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-03
 */
public class BaseEntity {

 //   @JsonIgnore
    private Integer page = 1;
 //   @JsonIgnore
    private Integer rows = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
