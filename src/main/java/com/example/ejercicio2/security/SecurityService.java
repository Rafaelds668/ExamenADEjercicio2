package com.example.ejercicio2.security;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public static Boolean validateToken(String token){return (token.equals("t0k3n"));}
}
