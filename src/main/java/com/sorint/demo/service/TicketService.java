package com.sorint.demo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import com.sorint.demo.service.data.avro.Ticket;
import com.sorint.demo.service.model.CountRecord;
import com.sorint.demo.service.model.PagePayload;
import com.sorint.demo.service.remote.TicketCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class TicketService {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public String findByTicketId(String ticketIdString) {
        IMap<Integer, Ticket> ticketMap = hazelcastInstance.getMap(ApplicationConstants.TicketsMapName);
        Ticket ticket = ticketMap.get(Integer.valueOf(ticketIdString));
        String jsonStr = "{}";
        try {
            jsonStr = ticket.toString();
        } catch(Exception e) {
            System.out.println("Ticket with id: " + ticketIdString + " not found.");
        }
        return jsonStr;
    }

    public String getItems(int page, int limit) {
        String jsonResult = "{}";

        IExecutorService executorService = hazelcastInstance.getExecutorService("CallableService");

        TicketCallable callable = new TicketCallable(page, limit);
        Future<PagePayload> task = executorService.submit(callable);

        try {
            jsonResult = task.get().toJsonString();
        } catch (InterruptedException e) {
            //do nothing
        } catch (ExecutionException e) {
            //do nothing
        } catch (Exception e) {
            //do nothing;
        }
        return jsonResult;
    }

    public String getCount() {
        IMap<Integer, Ticket> ticketMap = hazelcastInstance.getMap(ApplicationConstants.TicketsMapName);
        CountRecord count = new CountRecord(ApplicationConstants.TicketsMapName, ticketMap.size());
        return count.toJsonString();
    }
}
