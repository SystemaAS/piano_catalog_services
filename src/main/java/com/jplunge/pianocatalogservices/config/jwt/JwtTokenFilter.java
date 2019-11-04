package com.jplunge.pianocatalogservices.config.jwt;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
 

public class JwtTokenFilter extends GenericFilterBean {
    private String secret;
 
    private static final String BEARER = "Bearer";
    private UserDetailsService userDetailsService;
 
    public JwtTokenFilter (UserDetailsService userDetailsService, String secret) {
        this.userDetailsService = userDetailsService;
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
 
        String headerValue = ((HttpServletRequest)req).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token-> {
        	String username = getSubjectClaimFromToken((String)token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 
            if (username.equals(userDetails.getUsername()) && !isJwtExpired((String)token)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest)req));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        });
 
        filterChain.doFilter(req, res);
    }
 
    private Optional getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
 
    
    private String getSubjectClaimFromToken(String token) {
    	//System.out.println("Token:" + token);
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
 
   
    private Boolean isJwtExpired(String token) {
    	final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }
}