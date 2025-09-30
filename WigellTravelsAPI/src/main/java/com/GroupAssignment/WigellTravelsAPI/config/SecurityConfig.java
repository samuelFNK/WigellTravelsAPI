package com.GroupAssignment.WigellTravelsAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("WigellTravels API"));
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("ross")
                .password(passwordEncoder().encode("ross"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("rachel")
                .password(passwordEncoder().encode("rachel"))
                .roles("USER")
                .build();

        UserDetails user3 = User.withUsername("chandler")
                .password(passwordEncoder().encode("chandler"))
                .roles("USER")
                .build();

        UserDetails admin1 = User.withUsername("joey")
                .password(passwordEncoder().encode("joey"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, admin1);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
