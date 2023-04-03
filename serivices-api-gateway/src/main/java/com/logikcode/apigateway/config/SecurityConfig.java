package com.logikcode.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // needed because the api-gateway service is a webflux as against being a web mvc
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChainConfig(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.csrf().disable()
                .authorizeExchange(exchange-> exchange
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated()
                        //enabling the resource server capability
                ).oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
          return   serverHttpSecurity.build();
    }
}
