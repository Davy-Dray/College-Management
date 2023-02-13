package com.ragnar.MySchoolManagement.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	private final AccessDeniedHandler accessDeniedHandler;

	private final AuthorizationFilter authorizationFilter;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	
	public SecurityConfig(AccessDeniedHandler accessDeniedHandler, AuthorizationFilter authorizationFilter,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
		) {
		this.accessDeniedHandler = accessDeniedHandler;
		this.authorizationFilter = authorizationFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    
	    http
	    .csrf()
	    .disable()
	    .authorizeHttpRequests()
	    .requestMatchers("/**")
	    .permitAll();
	    
	    http
	    .authorizeHttpRequests()
        .requestMatchers("/**").hasAuthority("user:read");

	    
	    http
	    .sessionManagement()
	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    .and()
	    .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

	    
	    http
	    .exceptionHandling()
	    .accessDeniedHandler(accessDeniedHandler)
	    .authenticationEntryPoint(jwtAuthenticationEntryPoint);
	    
	    return http.build();
	  }
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

}
