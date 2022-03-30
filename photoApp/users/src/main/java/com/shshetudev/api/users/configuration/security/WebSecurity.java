package com.shshetudev.api.users.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private Environment env;

    public WebSecurity(Environment env) {
        this.env = env;
    }

    // This method receive HttpSecurity object as a method argument
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf = an attack that forces authenticated users to submit a request to a Web application
        // against which they are currently authenticated.
        http.csrf().disable();
        // permit all requests
        // http.authorizeRequests().antMatchers("/users/*").permitAll();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable(); // disable security check on h2 console api
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }
}
