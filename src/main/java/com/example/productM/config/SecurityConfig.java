package com.example.productM.config;

import com.example.productM.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/users/**", "/auth/**")
                        .permitAll()

                        .requestMatchers("/roles/**")
                        .hasRole("SUPER_ADMIN")

                        .requestMatchers("/categories/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/inventory/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/products/**")
                        .hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/addresses/**")
                        .hasRole("USER")

                        .requestMatchers("/cart/**")
                        .hasRole("USER")

                        .requestMatchers("/orders/**")
                        .hasRole("USER")

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(httpBasic -> {});

        return http.build();
    }
}