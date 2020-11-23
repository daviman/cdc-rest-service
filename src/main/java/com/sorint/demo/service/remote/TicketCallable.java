package com.sorint.demo.service.remote;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.map.IMap;
import com.sorint.demo.service.ApplicationConstants;
import com.sorint.demo.service.data.avro.Ticket;
import com.sorint.demo.service.model.PagePayload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TicketCallable implements Callable<PagePayload>, Serializable, HazelcastInstanceAware {

    private int page;

    private int limit;

    private HazelcastInstance hazelcastInstance;

    public TicketCallable(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * This is implemented inefficiently due to linear access, O(N squared)
     * @return
     * @throws Exception
     */
    @Override
    public PagePayload call() throws Exception {

        PagePayload payload = new PagePayload(page, limit);

        IMap<Integer, Ticket> ticketMap = hazelcastInstance.getMap(ApplicationConstants.TicketsMapName);
        int total = ticketMap.size();
        payload.setTotal(total);
        ticketMap.values();
        PayloadUtil.reduceToPage(payload, new ArrayList(ticketMap.values()));
        return payload;
    }

}
