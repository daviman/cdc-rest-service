package com.sorint.demo.service;

import com.sorint.demo.service.model.PagePayload;
import com.sorint.demo.service.remote.PayloadUtil;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PagePayloadTest {

    @Test
    public void testToJsonString() {
        PagePayload pagePayload = new PagePayload(1, 100);
        pagePayload.setPageCount(10);
        pagePayload.getItems().add("Line 1");
        pagePayload.getItems().add("Line 2");
        pagePayload.getItems().add("Line 3");
        pagePayload.getItems().add("Line 4");
        System.out.println(pagePayload.toJsonString());
    }

    @Test
    public void testReducePayload() {
        PagePayload pagePayload = new PagePayload(1, 100);

        List<String> list = new ArrayList<String>();

        IntStream.range(0, 201).forEach( i -> list.add("test-" + i));

        PayloadUtil.reduceToPage(pagePayload, list);
        System.out.println(pagePayload.toJsonString());
        Assert.assertEquals(pagePayload.getLimit(), pagePayload.getItems().size());

        pagePayload = new PagePayload(2, 100);
        PayloadUtil.reduceToPage(pagePayload, list);
        System.out.println(pagePayload.toJsonString());
        Assert.assertEquals(pagePayload.getLimit(), pagePayload.getItems().size());

        pagePayload = new PagePayload(3, 100);
        PayloadUtil.reduceToPage(pagePayload, list);
        Assert.assertEquals(1, pagePayload.getItems().size());

        System.out.println(pagePayload.toJsonString());
    }
}
