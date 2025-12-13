package com.chaski.Backend_chaski.security;

import com.chaski.Backend_chaski.service.FirebaseAuthService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FirebaseAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Permitir OPTIONS (CORS preflight)
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // Permitir endpoints públicos
        String path = request.getRequestURI();
        if (path.equals("/api/usuarios/registro") ||
            path.equals("/api/usuarios/login") ||
            path.startsWith("/api/usuarios/login/")) {
            return true;
        }

        // Obtener token del header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
            return false;
        }

        String token = authHeader.substring(7);

        try {
            // Verificar token de Firebase
            FirebaseToken decodedToken = firebaseAuthService.verifyIdToken(token);

            // Guardar info en request para uso posterior
            request.setAttribute("firebaseUid", decodedToken.getUid());
            request.setAttribute("email", decodedToken.getEmail());

            return true;

        } catch (FirebaseAuthException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido: " + e.getMessage());
            return false;
        }
    }
}
