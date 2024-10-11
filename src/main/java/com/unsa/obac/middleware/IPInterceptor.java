package com.unsa.obac.middleware;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IPInterceptor implements HandlerInterceptor {

    private List<String> allowedIPs = Arrays.asList("192.168.1.100", "203.0.113.5");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        
        if (allowedIPs.contains(ipAddress)) {
            return true; // Continuar con la solicitud
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Acceso denegado: su IP no está permitida.");
            return false; // Bloquear la solicitud
        }
    }
}
