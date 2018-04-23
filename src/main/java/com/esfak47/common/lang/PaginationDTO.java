package com.esfak47.common.lang;

import com.esfak47.common.utils.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginationDTO<T extends Serializable> implements Serializable {

    private int page;
    private int pageSize;
    private long total;
    private Collection<T> items;

    private PaginationDTO(int page, int pageSize, long total, List<T> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.items = items;
    }

    public static <F extends Serializable, T extends Serializable> PaginationDTO<T> convert(
        Function<? super F, ? extends T> converter,
        PaginationDTO<F> paginationDTO) {
        Assert.notNull(paginationDTO, "page should not be null");
        Assert.notNull(converter, "converter should not be null");
        Collection<F> items = paginationDTO.getItems();
        if (CollectionUtils.isEmpty(items)) {
            return createEmptyPaginationDTO(paginationDTO.getPage(), paginationDTO.getPageSize());
        } else {
            List<T> transform = items.stream().map(converter).collect(Collectors.toList());
            return PaginationDTO.createPaginationDTO(paginationDTO.getPage(), paginationDTO.getPageSize(),
                paginationDTO.getTotal(), transform);

        }
    }

    public static <T extends Serializable> PaginationDTO<T> createPaginationDTO(int page, int pageSize, long total,
                                                                                List<T> items) {
        return new PaginationDTO<T>(page, pageSize, total, items);
    }

    public static <T extends Serializable> PaginationDTO<T> createEmptyPaginationDTO(int page, int pageSize) {
        return new PaginationDTO<T>(page, pageSize, 0, Collections.<T>emptyList());
    }

    public int getPage() {
        return page;
    }

    public PaginationDTO setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PaginationDTO setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PaginationDTO setTotal(long total) {
        this.total = total;
        return this;
    }

    public Collection<T> getItems() {
        return items;
    }

    public PaginationDTO setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public Result<PaginationDTO<T>> asResult() {
        return Result.success(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PaginationDTO{");
        sb.append("page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}