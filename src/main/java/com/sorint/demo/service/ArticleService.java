package com.sorint.demo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import com.sorint.demo.service.data.avro.Article;
import com.sorint.demo.service.data.avro.Ticket;
import com.sorint.demo.service.model.CountRecord;
import com.sorint.demo.service.model.PagePayload;
import com.sorint.demo.service.remote.ArticleCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ArticleService {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public String findByArticleId(String articleIdString) {
        IMap<Integer, Article> articleMap = hazelcastInstance.getMap(ApplicationConstants.ArticlesMapName);
        Article ticket = articleMap.get(Integer.valueOf(articleIdString));
        String jsonStr = "{}";
        try {
            jsonStr = ticket.toString();
        } catch(Exception e) {
            System.out.println("Article with id: " + articleIdString + " not found.");
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
            //do nothing
        } catch (ExecutionException e) {
            //do nothing
        } catch (Exception e) {
            //do nothing;
        }
        return jsonResult;
    }

    public String getCount() {
        IMap<Integer, Article> articleMap = hazelcastInstance.getMap(ApplicationConstants.ArticlesMapName);
        CountRecord record = new CountRecord(ApplicationConstants.ArticlesMapName, articleMap.size());
        return record.toJsonString();
    }

}
