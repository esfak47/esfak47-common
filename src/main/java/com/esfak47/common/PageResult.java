package com.esfak47.common;

import com.esfak47.common.lang.Assert;
import com.esfak47.common.utils.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tony
 */
public class PageResult<T extends Serializable> implements Serializable {

    private int page;
    private int pageSize;
    private long total;
    private Collection<T> items;

    private PageResult(int page, int pageSize, long total, List<T> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.items = items;
    }


    public static <F extends Serializable, T extends Serializable> PageResult<T> convert(
        Function<? super F, ? extends T> converter,
        PageResult<F> pageResult) {
        Assert.notNull(pageResult, "page should not be null");
        Assert.notNull(converter, "converter should not be null");
        Collection<F> items = pageResult.getItems();
        if (CollectionUtils.isEmpty(items)) {
            return createEmptyPageResult(pageResult.getPage(), pageResult.getPageSize());
        } else {
            List<T> transform = items.stream().map(converter).collect(Collectors.toList());
            return PageResult.createPageResult(pageResult.getPage(), pageResult.getPageSize(),
                pageResult.getTotal(), transform);

        }
    }

    public static <T extends Serializable> PageResult<T> createPageResult(int page, int pageSize, long total,
                                                                          List<T> items) {
        return new PageResult<T>(page, pageSize, total, items);
    }

    public static <T extends Serializable> PageResult<T> createEmptyPageResult(int page, int pageSize) {
        return new PageResult<T>(page, pageSize, 0, Collections.<T>emptyList());
    }

    public int getPage() {
        return page;
    }

    public PageResult setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PageResult setTotal(long total) {
        this.total = total;
        return this;
    }

    public Collection<T> getItems() {
        return items;
    }

    public PageResult setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public Result<PageResult<T>> asResult() {
        return Result.success(this);
    }



}