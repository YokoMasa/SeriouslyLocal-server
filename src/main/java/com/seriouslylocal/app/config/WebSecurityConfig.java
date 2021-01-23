package com.seriouslylocal.app.config;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${adminPage.password}")
    private String adminPassword;

    @Value("${adminPage.username}")
    private String adminUsername;

    @Autowired
    private KeyProvider keyProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UserDetailsService uds = userDetailsService();
        http.authorizeRequests().antMatchers("/admin/api/**").authenticated();
        http.authorizeRequests().antMatchers("/admin/api/auth").permitAll();
        http.antMatcher("/admin/api/**").sessionManagement().disable();
        http.formLogin().loginProcessingUrl("/admin/api/auth").successHandler(new MyAuthSuccessHandler()).failureHandler(new MyAuthFailureHandler());
        http.userDetailsService(uds);
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND));
        http.addFilterBefore(new JwtAuthFilter(keyProvider, uds), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.cors();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager service = new InMemoryUserDetailsManager();
        UserDetails ud = User.builder().username(adminUsername).password("{noop}" + adminPassword).authorities("ADMIN")
                .build();
        service.createUser(ud);
        return service;
    }

    class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            Principal principal = (Principal) authentication;
            String jws = Jwts.builder().setSubject(principal.getName()).signWith(keyProvider.getKey()).compact();
            String responseJson = "{\"token\": \"" + jws + "\"}";
            response.setStatus(200);
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(responseJson);
        }

    }

    class MyAuthFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }

    }

}