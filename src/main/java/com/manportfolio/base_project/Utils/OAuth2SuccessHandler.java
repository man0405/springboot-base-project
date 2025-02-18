package com.manportfolio.base_project.Utils;

import com.manportfolio.base_project.Enum.AuthProvider;
import com.manportfolio.base_project.Models.User;
import com.manportfolio.base_project.Repository.Interface.UserRepository;
import com.manportfolio.base_project.Services.Implement.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

//
//@Component
//@RequiredArgsConstructor
//public class OAuth2SuccessHandler  implements AuthenticationSuccessHandler {
//
//    private final JwtService jwtService;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        User user = (User) authentication.getPrincipal();
//        String jwt = jwtService.generateToken(user);
//
//        response.sendRedirect("http://localhost:3000/login?token=" + jwt);
//    }
//}

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository; // Add this

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setProvider(AuthProvider.GOOGLE);
                    return userRepository.save(newUser);
                });

        String jwt = jwtService.generateToken(user);
        response.sendRedirect("http://localhost:3000/login?token=" + jwt);
    }
}