package com.eyescloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {


    @Autowired
    @Qualifier("detailService")
    private UserDetailsService detailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    // 客户端详情的配置
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //支持两种存储 内存存储 和JDBC存储  ，这次使用的是JDBC存储
        clients.inMemory()
                //注册应用程序的名称
                .withClient("eagleeye")
                // 密钥 thisissecret 该密钥在 应用程序调用OAuth2服务器以接收OAuth2 访问令牌时提供
                .secret("thisissecret")
                // 授权类型列表，这些授权类型将OAuth2 服务支持
                .authorizedGrantTypes("refresh_token" , "password" )
                //  用于定义调用应用程序在请求OAuth2 服务器获取令牌时可以操作的范围
                //
                .scopes("webclient" , "mobileclient")
                //授权的回调地址
                .redirectUris("http://baidu.com")
        ;

    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")//公开/oauth/token的接口
                .checkTokenAccess("permitAll()");
    }


    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(7200); // 默认有效期为2小时
        services.setRefreshTokenValiditySeconds(259200); // 默认有效期为3天
        return services;


    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.userDetailsService(detailsService)
                .authenticationManager(authenticationManager)
                .tokenServices(tokenServices())
        ;


    }


}
