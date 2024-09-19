package com.serp.spring.crawl.task;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class SpringCrawlTaskApplication {

	public static void main(String[] args) {
		RestClient restClient = RestClient.create();
		ResponseEntity<String> response = restClient.get()
				.uri("https://www.google.com/search?q=create+sprint+cloud+task+in+gcp+cloud+run&rlz=1C1CHBF_enIN1091IN1091&oq=create+sprint+cloud+task+in+gcp+cloud+run&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIJCAEQIRgKGKABMgkIAhAhGAoYoAHSAQg5MDA0ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(String.class);
		if (response.getStatusCode().value() == 200) {
			restClient.post()
					.uri("https://api.serpfactor.com/test")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ScrapResponse(response.getBody()))
					.retrieve()
					.toEntity(String.class);
		}
	}
}
