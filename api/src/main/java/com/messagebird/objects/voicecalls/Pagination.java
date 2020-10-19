package com.messagebird.objects.voicecalls;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pagination implements Serializable {

    private static final long serialVersionUID = -1107723710694243206L;

    private int totalCount;
    private int pageCount;
    private int currentPage;
    private int perPage;

}
