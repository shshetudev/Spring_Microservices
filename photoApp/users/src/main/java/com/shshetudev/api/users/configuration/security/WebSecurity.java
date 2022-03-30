package com.shshetudev.api.users.configuration.security;

import com.shshetudev.api.users.service.UsersService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private Environment environment;
    private UsersService usersService;
    private BCryptPasswordEncoder encoder;

    public WebSecurity(Environment env, UsersService usersService, BCryptPasswordEncoder encoder) {
        this.environment = env;
        this.usersService = usersService;
        this.encoder = encoder;
    }


    // This method receive HttpSecurity object as a method argument
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf = an attack that forces authenticated users to submit a request to a Web application
        // against which they are currently authenticated.
        http.csrf().disable();
        // permit all requests
        // http.authorizeRequests().antMatchers("/users/*").permitAll();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable(); // disable security check on h2 console api
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(encoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }
}
