//////package com.connectverse.connectverse.config;
//////
//////import lombok.RequiredArgsConstructor;
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.security.authentication.AuthenticationProvider;
//////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//////import org.springframework.security.config.http.SessionCreationPolicy;
//////import org.springframework.security.web.SecurityFilterChain;
//////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//////
//////@Configuration
//////@EnableWebSecurity
//////@RequiredArgsConstructor
//////public class SecurityConfiguration {
//////    private final JwtAuthenticationFilter jwtAuthFilter;
//////
//////    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
//////        this.jwtAuthFilter = jwtAuthFilter;
//////        this.authenticationProvider = authenticationProvider;
//////    }
//////
//////    private final AuthenticationProvider authenticationProvider;
//////
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//////        http.csrf()
//////                .disable().authorizeHttpRequests()
//////                .requestMatchers("")
//////                .permitAll()
//////                .anyRequest()
//////                .authenticated()
//////                .sessionManagement()
//////                .SessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////                .and()
//////                .authenticationProvider(authenticationProvider)
//////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//////
//////        return http.build();
//////    }
//////
//////}
////package com.connectverse.connectverse.config;
////
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import lombok.RequiredArgsConstructor;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationProvider;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////import java.io.IOException;
////
////@Configuration
////@EnableWebSecurity
////@RequiredArgsConstructor
////public class SecurityConfiguration {
////
////    private final JwtAuthenticationFilter jwtAuthFilter;
////    private final AuthenticationProvider authenticationProvider;
////
//////    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
//////        this.jwtAuthFilter = jwtAuthFilter;
//////        this.authenticationProvider = authenticationProvider;
//////    }
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(registry -> {
////                    registry.requestMatchers("/api/v1/auth/**").permitAll();
//////                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
//////                    registry.requestMatchers("/user/**").hasRole("USER");
////                    registry.anyRequest().authenticated();
////                })
//////                .formLogin(httpSecurityFormLoginConfigurer -> {
//////                    httpSecurityFormLoginConfigurer
//////                            .loginPage("/login")
//////                            .successHandler((request, response, authentication) -> {
//////
//////                            })
//////                            .permitAll();
//////                })
//////                .requestMatchers("") // Define the public endpoints
//////                .permitAll()
//////                .anyRequest()
//////                .authenticated()
//////                .and()
//////                .sessionManagement()
//////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////                .and()
////                .authenticationProvider(authenticationProvider)
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
////}
//package com.connectverse.connectverse.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/api/v1/auth/**").permitAll();
//                    registry.requestMatchers("/api/v1/welcome").permitAll();
//                    // User-specific endpoints
//                    registry.requestMatchers("/api/v1/user/**").hasRole("USER");
//
//                    // Admin-specific endpoints
//                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
//                    registry.anyRequest().authenticated();
//                })
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
package com.connectverse.connectverse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    // Public endpoints
                    registry.requestMatchers("/api/v1/auth/**",
                                    "/v2/api-docs",
                                    "v3/api-docs",
                                    "v3/api-docs/**",
                                    "swagger-resources",
                                    "swagger-resources/**",
                                    "configuration/ui",
                                    "configuration/security",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/swagger-ui.html")
                            .permitAll();
                    registry.requestMatchers("/api/v1/welcome").permitAll();

                    // User-specific endpoints
//                    registry.requestMatchers("/api/v1/user/**").hasRole("USER");
//
//                    // Admin-specific endpoints
//                    registry.requestMatchers("/admin/**").hasRole("ADMIN");

                    // Any other request must be authenticated
                    registry.anyRequest().authenticated();
                })
//                .cors(cors -> cors.disable())
                .cors(withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
