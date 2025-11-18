package com.learn.springai.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springai.model.CountryCities;

@RestController
public class StructuredOutputController {

        private final ChatClient chatClient;

        public StructuredOutputController(ChatClient.Builder builder) {
                this.chatClient = builder
                                .defaultAdvisors(new SimpleLoggerAdvisor()).build();
        }

        @GetMapping("chat/entity")
        public ResponseEntity<CountryCities> getCountryCities(@RequestParam String country) {
                CountryCities response = chatClient
                                .prompt()
                                .user(prompt -> prompt.text("Provide list of major cities in {country}")
                                                .param("country", country))
                                .call()
                                .entity(CountryCities.class);

                return ResponseEntity.ok(response);
        }

        @GetMapping("chat/list")
        public ResponseEntity<List<String>> getCountryCitiesList(@RequestParam String country) {
                List<String> response = chatClient
                                .prompt()
                                .user(prompt -> prompt.text("Provide a list of major cities in {country}")
                                                .param("country", country))
                                .call()
                                .entity(new ListOutputConverter());

                return ResponseEntity.ok(response);
        }

        @GetMapping("chat/map")
        public ResponseEntity<Map<String, Object>> getStateCapitals(@RequestParam String country) {
                Map<String, Object> response = chatClient
                                .prompt()
                                .user(prompt -> prompt.text("Provide states and their capitals in country {country}")
                                                .param("country", country))
                                .call()
                                .entity(new MapOutputConverter());

                return ResponseEntity.ok(response);
        }

        @GetMapping("chat/listbeans")
        public ResponseEntity<List<CountryCities>> getListCountryCities(@RequestParam String continent) {
                List<CountryCities> response = chatClient
                                .prompt()
                                .user(prompt -> prompt.text(
                                                "Provide the list of country and their major cities in continent {continent}")
                                                .param("continent", continent))
                                .call()
                                .entity(new ParameterizedTypeReference<List<CountryCities>>() {

                                });

                return ResponseEntity.ok(response);
        }
}
