package com.manportfolio.base_project.Utils;

import com.manportfolio.base_project.Models.User;
import com.manportfolio.base_project.Services.Implement.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler  implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String jwt = jwtService.generateToken(user);

        response.sendRedirect("http://localhost:3000/login?token=" + jwt);
    }
}
