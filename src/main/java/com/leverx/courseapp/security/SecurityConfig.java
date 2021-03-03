package com.leverx.courseapp.security;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.AuthorizationMode;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${okta.group.users.id}")
    private String groupId;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Value("${okta.client.org-url}")
    private String orgUrl;

    @Value("${okta.client.token}")
    private String clientToken;

    @Bean
    Client client() {
        var client = Clients.builder()
                .setAuthorizationMode(AuthorizationMode.SSWS)
                .setClientId(clientId)
                .setOrgUrl(orgUrl)
                .setClientCredentials(new TokenClientCredentials(clientToken))
                .build();
        return client;
    }

    @Bean
    UserBuilder userBuilder() {
        var userBuilder = UserBuilder.instance().addGroup(groupId);
        return userBuilder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Client()
                .and()
                .oauth2Login();
    }

    @Override
    @Profile("local")
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
