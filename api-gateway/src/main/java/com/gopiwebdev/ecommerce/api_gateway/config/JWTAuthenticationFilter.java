package com.gopiwebdev.ecommerce.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
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
        // Extract JWT from the Authorization header
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            if (validateToken(token)) {
                // Forward the valid token to the backend service
                exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                // Handle invalid token (e.g., 401 Unauthorized)
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // If no token is provided, you can reject the request or forward without JWT
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
