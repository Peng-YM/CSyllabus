//package com.peng1m.springboot.settings.security;
//
//
//import com.peng1m.springboot.service.impl.MyUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//@Configuration
//public class OAuth2ServerConfiguration{
//    private static final String RESOURCE_ID = "restservice";
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//        @Autowired
//        private TokenStore tokenStore;
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources){
//            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .authorizeRequests()
//                    .antMatchers("/api/**").authenticated();
//        }
//    }
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//        @Autowired
//        private TokenStore tokenStore;
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Autowired
//        MyUserDetailsService userDetailsService;
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints
//                    .tokenStore(tokenStore)
//                    .authenticationManager(authenticationManager)
//                    .accessTokenConverter(jwtAccessTokenConverter)
//                    .userDetailsService(userDetailsService);
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients
//                    .inMemory()
//                    .withClient("clientapp")
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .authorities("ROLE_USER")
//                    .scopes("read", "write")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("123456");
//        }
//    }
//}
