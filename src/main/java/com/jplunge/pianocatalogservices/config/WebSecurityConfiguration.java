package com.jplunge.pianocatalogservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.jplunge.pianocatalogservices.config.jwt.JwtTokenFilter;
 
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Value("${security.jwt.token.secret-key}")
    private String secret;
	
    @Autowired
    private UserDetailsService userDetailsService;
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.csrf().disable()
        	.authorizeRequests()
            .antMatchers("/signin").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        	http.cors(); //must be here for CRUD operations
        	
        //add Jwt Filter
        http.addFilterBefore(new JwtTokenFilter(userDetailsService, secret), UsernamePasswordAuthenticationFilter.class);
        
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
 
        return new InMemoryUserDetailsManager(user);
    }
}