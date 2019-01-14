package com.messagebird;

public abstract class Paging {

    private Paging() {}

    public static class SlicedPaging extends Paging {
        final Integer offset;
        final Integer limit;

        public SlicedPaging(Integer offset, Integer limit) {
            this.offset = offset;
            this.limit = limit;
        }
    }

    public static class PagedPaging extends Paging {
        final Integer page;
        final Integer pageSize;

        public PagedPaging(Integer page, Integer pageSize) {
            this.page = page;
            this.pageSize = pageSize;
        }
    }
}
