package com.sorint.demo.service.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagePayload implements Serializable {

    private int page = 0;
    private int pageCount = 0;
    private int limit = 10;
    private int total;

    private List<String> items = new ArrayList<>();

    public PagePayload(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            return json;
        } catch(Exception e) {
            return e.getLocalizedMessage();
        }
    }
}
