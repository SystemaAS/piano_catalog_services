package com.jplunge.pianocatalogservices;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jplunge.pianocatalogservices.config.OwnConfigurator;
import com.jplunge.pianocatalogservices.config.WebConfig;
import com.jplunge.pianocatalogservices.config.jwt.JwtProvider;
import com.jplunge.pianocatalogservices.model.SignInDto;

@SpringBootApplication
//@CrossOrigin(origins = { "*" })
@RestController
public class PianoCatalogServicesApplication {

	@Autowired //to load some own configuration (just as an example ...)
	private OwnConfigurator config;
	
	@Autowired //to load CORS (CrossOrigin in a global manner) ... NOT WORKING
	private WebConfig webConfig;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtProvider jwtProvider;
	
	public static void main(String[] args) {
		SpringApplication.run(PianoCatalogServicesApplication.class, args);
	}
	
	@RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
	
	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}
	
	/** For testing without JWT
	@PostMapping("/signin")
    public Authentication signIn(@RequestBody @Valid SignInDto signInDto) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
    }**/
	
	@PostMapping("/signin")
    public String signIn(@RequestBody @Valid SignInDto signInDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
            return jwtProvider.createToken(signInDto.getUsername());
        } catch (AuthenticationException e){
            System.out.println("Log in failed for user, " + signInDto.getUsername());
        }
 
        return "";
    }

}
