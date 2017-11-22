package com.peng1m.springboot.settings.security;

import com.peng1m.springboot.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private AuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
//        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/api/**").hasAnyRole("ROLE_ADMIN, ROLE_USER")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login")
                    .usernameParameter("name")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home")
                .and()
                    .logout().logoutSuccessUrl("/login")
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity
                .ignoring()
                .antMatchers("/webjars/**","/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("ASDFASFsdfsdfsdfsfadsf234asdfasfdas");
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
