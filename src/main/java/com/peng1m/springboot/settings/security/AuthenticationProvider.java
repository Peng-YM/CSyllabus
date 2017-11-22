//package com.peng1m.springboot.settings.security;
//
//import com.peng1m.springboot.service.impl.MyUserDetailsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
//        logger.debug("> additionalAuthenticationChecks");
//        if (token.getCredentials() == null || userDetails.getPassword() == null){
//            throw new BadCredentialsException("Credentials may not be null");
//        }
//
//        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())){
//            throw new BadCredentialsException("Invalid credential!");
//        }
//        logger.debug("< additionalAuthenticationChecks");
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        logger.debug("> retrieveUser");
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        logger.debug("< retrieveUser");
//        return userDetails;
//    }
//}
