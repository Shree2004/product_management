package com.example.productM.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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

                        .requestMatchers("/auth/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/users")
                        .permitAll()

                        .requestMatchers("/roles/**")
                        .hasRole("SUPER_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/users/**")
                        .hasRole("SUPER_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/users/**")
                        .hasRole("SUPER_ADMIN")

                        .requestMatchers("/categories/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/inventory/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/products/**")
                        .hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/products/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/products/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/products/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

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