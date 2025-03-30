// package com.melkeinkood.ticket_guru.auth.security;

// import com.melkeinkood.ticket_guru.auth.services.JwtService;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;
// import java.io.IOException;

// public class JwtFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;

//     public JwtFilter(JwtService jwtService) {
//         this.jwtService = jwtService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
        
//         // Get the Authorization header from the request
//         String token = request.getHeader("Authorization");

//         if (token != null && token.startsWith("Bearer ")) {
//             token = token.substring(7);  // Remove "Bearer " from the token string
            
//             // Validate the token
//             if (jwtService.validateToken(token)) {
//                 // Extract username from the token
//                 String username = jwtService.getUsernameFromToken(token);

//                 // Create an authentication object for Spring Security
//                 UsernamePasswordAuthenticationToken authenticationToken =
//                         new UsernamePasswordAuthenticationToken(username, null, null);
//                 // Set the authentication object in the security context
//                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//             }
//         }

//         // Continue the request-response cycle
//         filterChain.doFilter(request, response);
//     }
// }
