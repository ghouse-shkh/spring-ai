package com.learn.springai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.springai.entity.HelpTicket;
import com.learn.springai.model.TicketRequest;
import com.learn.springai.repository.HelpTicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HelpTicketService {

    private final HelpTicketRepository helpTicketRepository;

    public HelpTicket createHelpTicket(TicketRequest ticketRequest, String username) {
        HelpTicket helpTicket = HelpTicket.builder()
                .username(username)
                .issue(ticketRequest.issue())
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(3))
                .build();
        return helpTicketRepository.save(helpTicket);
    }

    public List<HelpTicket> getHelpTicketsByUsername(String username) {
        return helpTicketRepository.findByUsername(username);
    }
}
