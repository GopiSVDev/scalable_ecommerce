package com.gopiwebdev.ecommerce.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JWTAuthenticationFilter implements WebFilter {
    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        HttpMethod method = exchange.getRequest().getMethod();

        if (path.equals("/api/users/login") || path.equals("/api/users/register")) {
            return chain.filter(exchange);
        }

        if (path.equals("/api/products/create") && method.equals(HttpMethod.POST)) {
            return authenticateRequest(exchange, chain);
        }

        if (path.startsWith("/api/users/") || path.startsWith("/api/cart/")) {
            return authenticateRequest(exchange, chain);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> authenticateRequest(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (validateToken(token)) {
                exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // Use a secure key
                    .build();
            // Parse the JWT and validate the signature
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }
}
