package com.uefa.fifamanager.service;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String username;
    private String token;
    Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationResponse(String token,String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.token = token;
    }
    
    public JwtAuthenticationResponse(String username, Collection<? extends GrantedAuthority> authorities) {
    	this.username = username;
        this.authorities = authorities;
    }

    public String getToken()
    {
    	return this.token;
    }
    
    public String getUsername() {
        return this.username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}