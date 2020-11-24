package com.sorint.demo.service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article")
    public String getTicketById(
            @RequestParam(value = "ticketId"   , required = true) int ticketId,
            @RequestParam(value = "articleId"  , required = true) int articleId
        )
    {
        return articleService.findByTicketIdAndArticleId(ticketId, articleId);
    }

    @GetMapping()
    public String getArticles(
            @RequestParam(value = "page"  , defaultValue =  "1" , required = false) int page,
            @RequestParam(value = "limit" , defaultValue = "10",  required = false) int limit
        )
    {
        //return "get user method got called with page number = " + page + "      with limit = " + limit;
        return articleService.getItems(page, limit);
    }

    @RequestMapping("/records")
    public String getCount() {
        return articleService.getCount();
    }

}
