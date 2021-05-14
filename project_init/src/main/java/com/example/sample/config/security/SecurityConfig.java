package com.example.sample.config.security;

import com.example.sample.config.CustomUserDetailsService;
import com.example.sample.config.jwt.JwtAuthenticationEntryPoint;
import com.example.sample.config.jwt.JwtAuthenticationFilter;
import com.example.sample.config.jwt.RestAccessDeniedHandler;
import com.example.sample.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final AuthenticationManager authenticationManager;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          @Lazy AuthenticationManager authenticationManager,
                          RestAccessDeniedHandler restAccessDeniedHandler) {

        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationManager = authenticationManager;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        List<String> pathsToSkip = Collections.singletonList(Constants.ApiEndpoint.PUBLIC_API_PREFIX);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, Constants.ApiEndpoint.AUTH_API_PREFIX);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin().and()
                .cors()
                .and()
                .csrf()
                .disable()
//                .exceptionHandling()
//                .accessDeniedHandler(restAccessDeniedHandler)
//                .authenticationEntryPoint(unauthorizedHandler)
                //.and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(Constants.SWAGGER_URLS).permitAll()
                .and()
                .authorizeRequests().antMatchers(Constants.ApiEndpoint.PUBLIC_API_PREFIX).permitAll()
                .and()
                .authorizeRequests().antMatchers(Constants.ApiEndpoint.AUTH_API_PREFIX).authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
