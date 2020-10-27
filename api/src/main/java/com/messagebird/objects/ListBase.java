package com.messagebird.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Provides an object to deserialize to for endpoints returning listings.
 *
 * @param <T> Type of the items, e.g. Contact or Message.
 */
@ToString
@Getter
@NoArgsConstructor
public class ListBase<T> {

    private Integer offset;
    private Integer limit;
    private Integer totalCount;
    private Links links;
    @Setter
    private List<T> items;
}
