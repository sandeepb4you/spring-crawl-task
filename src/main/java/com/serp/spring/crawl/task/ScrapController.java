package com.serp.spring.crawl.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/")
public class ScrapController {

    @GetMapping("scrap")
    public ResponseEntity<ScrapResponse> getProjects(@RequestParam("path") String path) {

        RestClient restClient = RestClient.create();
        ResponseEntity<String> response = restClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        return new ResponseEntity<>(new ScrapResponse(response.getBody(), response.getStatusCode().value()), HttpStatus.OK);
    }
}
