package com.logikcode.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // returning WebClient.Build and annotating with @Loadbalanced enables auto instance service calls
    // and  load balancing by the eureka discovery
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
   // Bean Configuration for WebClient for service calls;
    //Note: with no load balancing
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
