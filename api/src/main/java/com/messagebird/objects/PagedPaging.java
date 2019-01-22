package com.messagebird.objects;

import java.io.Serializable;

public class PagedPaging implements Serializable {

    private static final long serialVersionUID = 5764858762655505158L;
    private Integer page;
    private Integer pageSize;

    public PagedPaging(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
