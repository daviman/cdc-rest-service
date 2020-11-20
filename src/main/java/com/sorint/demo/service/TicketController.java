package com.sorint.demo.service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public String getTicketById(@PathVariable("ticketId") String ticketIdString) {
        return ticketService.findByTicketId(ticketIdString);
    }

    @GetMapping()
    public String getTickets(
            @RequestParam(value = "page"  , defaultValue =  "1" , required = false) int page,
            @RequestParam(value = "limit" , defaultValue = "10",  required = false) int limit
        )
    {
        return ticketService.getItems(page, limit);
    }

    @RequestMapping("/records")
    public String getCount() {
        return ticketService.getCount();
    }
}
