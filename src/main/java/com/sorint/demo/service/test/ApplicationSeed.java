package com.sorint.demo.service.test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.sorint.demo.service.ApplicationConstants;
import com.sorint.demo.service.data.avro.Article;
import com.sorint.demo.service.data.avro.Ticket;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

public class ApplicationSeed {

    static Random random = new Random();

    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();

        IMap<Integer, Ticket> ticketMap = hazelcastInstance.getMap(ApplicationConstants.TicketsMapName);
        IMap<Integer, Article> articleMap = hazelcastInstance.getMap(ApplicationConstants.ArticlesMapName);
        IntStream.range(0, 1001).forEach(i ->
                {
                    Ticket ticket = createTicket(i);
                    ticketMap.put(ticket.getId(), ticket);
                }
        );

        IntStream.range(0, 1001).forEach(i ->
                {
                    Article article = createArticle(i);
                    articleMap.put(article.getId(), article);
                }
        );

        hazelcastInstance.shutdown();
    }

    private static Ticket createTicket(int ticketId) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setTitle("My Test Title: " + ticketId);
        setTicketAttributes(ticket);
        return ticket;
    }

    private static Article createArticle(int articleId) {
        Article article = new Article();
        article.setId(articleId);
        setArticleAttributes(article);
        return article;
    }

    private static void setArticleAttributes(Article article) {
        article.setChangeTime(System.currentTimeMillis());
        article.setChangeTimeDate(new Date());
        article.setCreateTime(System.currentTimeMillis());
        article.setABody(RandomStringUtils.randomAlphabetic(random.nextInt(1000)));
        article.setCreateTimeDate(new Date());
        article.setACc("ACc:" + article.getId());
        article.setAContentType("AContentType:" + article.getId());
        article.setAFrom("AFrom:" + article.getId());
        article.setAInReplyTo("AinReplyTo:" + article.getId());
        article.setAMessageId("" + article.getId());
        article.setAMessageIdMd5("" + article.getId());
        article.setAReferences("AReferences:" + article.getId());
        article.setASubject("A Subject: " + article.getId());
        article.setArticleSenderTypeId(article.getId());
    }

    private static void setTicketAttributes(Ticket ticket) {
        ticket.setChangeTime(System.currentTimeMillis());
        ticket.setCreateTime(System.currentTimeMillis());
        ticket.setChangeTimeDate(new Date());
        ticket.setCreateTimeDate(new Date());
        ticket.setArchiveFlag(0);
        ticket.setChangeBy(100);
        ticket.setCreateBy(100);
        ticket.setCustomerId("Test:" + ticket.getId());
        ticket.setCustomerUserId("TestUser:" + ticket.getId());
        ticket.setCreateTimeUnix(System.currentTimeMillis());
        ticket.setEscalationResponseTime(1);
        ticket.setEscalationSolutionTime(10);
        ticket.setQueueId(20);
        ticket.setResponsibleUserId(11);
        ticket.setServiceId(12000);
        ticket.setSlaId(99);
        ticket.setTicketLockId(99);
        ticket.setTicketPriorityId(99);
        ticket.setTicketStateId(99);
        ticket.setTimeout(99);
        ticket.setTn("Tn:" + ticket.getId());
        ticket.setTypeId(99);
        ticket.setUntilTime(2000);
        ticket.setUserId(99);
        ticket.setEscalationSolutionTime(99);
    }
}
