package com.web.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository repo)
            throws Exception {

        var base_uri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
        var resolver = new DefaultOAuth2AuthorizationRequestResolver(repo, base_uri);

        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

        http
                .authorizeRequests(a -> a
                        .antMatchers("/").permitAll()
                        .antMatchers("/api/v1/**").permitAll()
                        .antMatchers("/images/**").permitAll()
                        .antMatchers("/css/**").permitAll()
                        .antMatchers("/fonts/**").permitAll()
                        .antMatchers("/js/**").permitAll()
                        .antMatchers("/img/**").permitAll()
                        .antMatchers("/libs.gsap/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(login -> login.authorizationEndpoint().authorizationRequestResolver(resolver));

        http.logout(logout -> logout
                .logoutSuccessUrl("/"));

        http.csrf().disable();
        return http.build();
    }
}
