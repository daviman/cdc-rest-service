package com.sorint.demo.service.remote;

import com.sorint.demo.service.model.PagePayload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PayloadUtil implements Serializable {

    private static void setPageCount(PagePayload pagePayload) {
        BigDecimal number = new BigDecimal(pagePayload.getTotal());
        BigDecimal divisor = new BigDecimal(pagePayload.getLimit());
        BigDecimal[] result = number.divideAndRemainder(divisor);
        int pageCount = result[0].intValue() +
            (
                    (result[1].intValue() > 0) ? 1 : 0
            );
        pagePayload.setPageCount(pageCount);
    }

    public static void reduceToPage(PagePayload pagePayload, List<?> collection) {
        pagePayload.setTotal(collection.size());
        setPageCount(pagePayload);
        int intervalStart = (pagePayload.getPage() == 0 ? 0 : pagePayload.getPage()-1) * pagePayload.getLimit();
        int intervalEnd = intervalStart + pagePayload.getLimit();
        if(intervalEnd > pagePayload.getTotal()) {
            intervalEnd = pagePayload.getTotal();
        }
        List<?> result = collection.subList(intervalStart, intervalEnd);
        for (Object o : result) {
            pagePayload.getItems().add(o.toString());
        }
    }
}
