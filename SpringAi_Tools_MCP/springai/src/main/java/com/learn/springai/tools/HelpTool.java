package com.learn.springai.tools;

import java.util.List;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.learn.springai.entity.HelpTicket;
import com.learn.springai.model.TicketRequest;
import com.learn.springai.service.HelpTicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelpTool {

    private final HelpTicketService helpTicketService;

    @Tool(name="createTicket", description = "Create a new ticket for Help")
    String createTicket(@ToolParam(description = "Details to create a help ticket") TicketRequest request, ToolContext context) {
        log.info("Creating help ticket with request: {}", request);
        String username = (String)context.getContext().get("username"); 

        HelpTicket ticket = helpTicketService.createHelpTicket(request, username);
        log.info("Created help ticket with ID: {}", ticket.getId());    
        return "Help ticket created with ID: " + ticket.getId() + " for user: " + ticket.getUsername();
                        
    }

    @Tool(name="getTickets", description = "Get the status of open tickets based on given username")
    List<HelpTicket> getTicketsByUsername(ToolContext context) {
        String username = (String)context.getContext().get("username"); 
        log.info("Fetching help tickets for user: {}", username);
        return helpTicketService.getHelpTicketsByUsername(username);
    }

    
}
