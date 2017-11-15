package com.peng1m.springboot.config;

import com.peng1m.springboot.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/login", "/registered").permitAll()
                    .antMatchers("/api/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
                .and()
                .logout()
                    .permitAll()
                    .and()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }
}
