package com.eyescloud.config;

import com.eyescloud.handler.WebLoginAuthSuccessHandler;
import com.eyescloud.handler.WebLoginFailureHandler;
import com.eyescloud.handler.WebLogoutHandler;
import com.eyescloud.handler.WebLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("detailService")
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    //  AuthenticationManagerBean 被Spring Security 用来处理验证
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .logout()
                .addLogoutHandler(logoutHandler()).logoutSuccessHandler(logoutSuccessHandler())
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .addFilterBefore(usernamePasswordAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        return  provider;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        }catch (Exception e){
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(loginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }


    @Bean
    public LogoutHandler logoutHandler() {
        WebLogoutHandler logoutHandler = new WebLogoutHandler();
        return  logoutHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        WebLogoutSuccessHandler logoutSuccessHandler = new WebLogoutSuccessHandler();
        return  logoutSuccessHandler;
    }

    @Bean
    public WebLoginAuthSuccessHandler loginAuthSuccessHandler() {
        WebLoginAuthSuccessHandler webLoginAuthSuccessHandler = new WebLoginAuthSuccessHandler();
        return webLoginAuthSuccessHandler;
    }


    @Bean
    public WebLoginFailureHandler loginFailureHandler() {
        WebLoginFailureHandler webLoginFailureHandler = new WebLoginFailureHandler();
        return webLoginFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }



}
