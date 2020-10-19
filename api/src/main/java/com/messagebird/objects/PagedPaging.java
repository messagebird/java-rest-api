package com.messagebird.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class PagedPaging implements Serializable {

    private static final long serialVersionUID = 5764858762655505158L;
    private Integer page;
    private Integer pageSize;
}
