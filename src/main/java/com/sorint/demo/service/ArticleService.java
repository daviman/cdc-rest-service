package com.sorint.demo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import com.sorint.demo.service.data.avro.Article;
import com.sorint.demo.service.data.avro.ArticleKey;
import com.sorint.demo.service.data.avro.Ticket;
import com.sorint.demo.service.model.CountRecord;
import com.sorint.demo.service.model.PagePayload;
import com.sorint.demo.service.remote.ArticleCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ArticleService {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public String findByTicketIdAndArticleId(int ticketId, int articleId) {
        IMap<ArticleKey, Article> articleMap = hazelcastInstance.getMap(ApplicationConstants.ArticlesMapName);
        ArticleKey articleKey = new ArticleKey(articleId, ticketId);
        Article ticket = articleMap.get(articleKey);
        String jsonStr = "{}";
        try {
            String asciiEncodedString = new String(ticket.getABody().toString().getBytes(), StandardCharsets.US_ASCII);
            ticket.setABody(asciiEncodedString);
            jsonStr = ticket.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Article with ticketId: %d and articleId: %d not found.", ticketId, articleId));
        }
        return jsonStr;
    }

    public String getItems(int page, int limit) {
        String jsonResult = "{}";

        IExecutorService executorService = hazelcastInstance.getExecutorService("CallableService");

        ArticleCallable callable = new ArticleCallable(page, limit);
        Future<PagePayload> task = executorService.submit(callable);

        try {
            jsonResult = task.get().toJsonString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public String getCount() {
        IMap<Integer, Article> articleMap = hazelcastInstance.getMap(ApplicationConstants.ArticlesMapName);
        CountRecord record = new CountRecord(ApplicationConstants.ArticlesMapName, articleMap.size());
        return record.toJsonString();
    }

}
