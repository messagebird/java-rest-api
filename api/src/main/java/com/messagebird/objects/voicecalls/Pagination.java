package com.messagebird.objects.voicecalls;

import java.io.Serializable;

public class Pagination implements Serializable {

    private static final long serialVersionUID = -1107723710694243206L;

    private int totalCount;
    private int pageCount;
    private int currentPage;
    private int perPage;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", currentPage=" + currentPage +
                ", perPage=" + perPage +
                '}';
    }
}
