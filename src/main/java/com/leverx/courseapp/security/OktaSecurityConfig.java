package com.leverx.courseapp.security;

import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OktaSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${okta.client.org-url}")
    private String oktaDomain;

    @Bean
    AuthenticationClient authenticationClient() {
        var client = AuthenticationClients.builder().setOrgUrl(oktaDomain).build();
        return client;
    }

    //    @Component("userSecurity")
    //    public class UserSecurity {
    //        public boolean hasUserEmail(JwtAuthenticationToken authentication, String email) {
    //            return authentication.getName().equals(email);
    //        }
    //    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/courses/**", "/tags/**", "/tasks/**", "/h2/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/students")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Client()
                .and()
                .oauth2Login();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
