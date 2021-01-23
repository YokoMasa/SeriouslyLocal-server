package com.seriouslylocal.app.config;

import java.security.Key;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KeyProvider {

    private Key key;
    
    @PostConstruct
    private void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public Key getKey() {
        return key;
    }

}
