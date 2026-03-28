package com.school.ppmg.computer_equipment_store_system_api.config;

import com.school.ppmg.computer_equipment_store_system_api.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/attributes/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/services/active").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/services/*").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/services").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/services").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/services/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/services/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/attributes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/attributes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/attributes/**").hasRole("ADMIN")

                        .requestMatchers("/api/cart/**").hasAnyRole("CUSTOMER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/orders/checkout").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/api/orders/my/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/*/status").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/service-requests").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/api/service-requests/my/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/service-requests").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/service-requests/*/status").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}