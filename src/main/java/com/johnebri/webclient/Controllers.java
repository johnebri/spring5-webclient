package com.johnebri.webclient;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1")
public class Controllers {

	Logger log = LoggerFactory.getLogger(Controllers.class);

	WebClient webClient;

	@PostConstruct
	public void init() {
		webClient = WebClient.builder().baseUrl("https://api.creditRegistry.org/nigeria/AutoCred/v7.Test")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).filter(logRequest())
				.filter(logResponse()).build();

	}

	@PostMapping("/login")
	public Mono<String> Login(@RequestBody LoginDto loginDto) {
		return webClient.post().uri("/api/Agents/Login").syncBody(loginDto).retrieve()
				
				.onStatus(HttpStatus::is4xxClientError,
						clientResponse -> Mono.error(new CustomException("404 unsorported exception")))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new CustomException("400 Server exception")))
				
				.bodyToMono(String.class);
	}

////	@PostMapping("/login")
////	public LoginDto Login(@RequestBody LoginDto loginDto) {
////		return loginDto;
////	}
//	
////	public Flux<LoginDto> getAllLogins() {
////		return webClient.get().uri("/test/getall").retrieve().bodyToFlux(LoginDto.class);
////	}

//	@GetMapping("/trackBooking/{bookingId}")
//	public Mono<BookRequest> getBookingById(@PathVariable int bookingId) {
//		return webClient.get().uri("").retrieve().bodyToMono(BookRequest.class);
//	}

//	@DeleteMapping("/deleteBooking/{bookingId}")
//	public Mono<String> deleteBooking(int bookingId) {
//		return webClient.delete().uri("").retrieve().bodyToMono(String.class);
//	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("Request {} {} ", clientRequest.method(), clientRequest.url());
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			log.info("Response Status Code {} ", clientResponse.statusCode());
			return Mono.just(clientResponse);
		});
	}

}
