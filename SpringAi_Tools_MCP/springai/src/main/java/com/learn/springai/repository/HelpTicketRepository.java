package com.learn.springai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.springai.entity.HelpTicket;

public interface HelpTicketRepository extends JpaRepository<HelpTicket, Long> {

    List<HelpTicket> findByUsername(String username);

}
