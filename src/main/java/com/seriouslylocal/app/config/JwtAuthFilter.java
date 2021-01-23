package com.seriouslylocal.app.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthFilter implements Filter {

    private KeyProvider keyProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthFilter(KeyProvider keyProvider, UserDetailsService userDetailsService) {
        this.keyProvider = keyProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            doFilterHttp(httpRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }
        
        String rawAuthHeader = request.getHeader("Authorization");
        if (rawAuthHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        String prefix = "Bearer ";
        if (!rawAuthHeader.startsWith(prefix)) {
            response.setStatus(404);
            return;
        }

        String token = rawAuthHeader.substring(7);
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(keyProvider.getKey()).build().parseClaimsJws(token).getBody();
            if (claims != null) {
                Authentication authentication = createAuthentication(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } else {
                response.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
        }
    }

    private Authentication createAuthentication(Claims claims) throws Exception {
        String subject = claims.getSubject();
        // System.out.println("subject: " + subject);
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

        return new Authentication() {

			@Override
			public String getName() {
				return userDetails.getUsername();
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
                return userDetails.getAuthorities();
			}

			@Override
			public Object getCredentials() {
				return "";
			}

			@Override
			public Object getDetails() {
				return "";
			}

			@Override
			public Object getPrincipal() {
				return userDetails;
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				
			}

        };
    }
    
}
