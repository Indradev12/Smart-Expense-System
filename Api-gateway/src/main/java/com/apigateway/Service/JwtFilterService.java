// package com.apigateway.Service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.core.Ordered;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;

// @Component
// public class JwtFilterService implements GlobalFilter, Ordered {

//     @Autowired
//     private JwtUtilService jwtUtil;

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         String path = exchange.getRequest().getPath().toString();
//         System.out.println("[DEBUG] Incoming request path: " + path);

//         String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//         System.out.println("[DEBUG] Incoming Auth Header: " + authHeader);

//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String jwt = authHeader.substring(7);
//             if (jwtUtil.validateToken(jwt)) {
//                 System.out.println("[DEBUG] JWT validated successfully.");
//                 return chain.filter(exchange);
//             } else {
//                 System.out.println("[DEBUG] JWT validation failed.");
//                 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                 return exchange.getResponse().setComplete();
//             }
//         }

//         System.out.println("[DEBUG] No Authorization header found");
//         exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//         return exchange.getResponse().setComplete();
//     }

//     // Set lower order so it runs early
//     @Override
//     public int getOrder() {
//         return -1;
//     }
// }
