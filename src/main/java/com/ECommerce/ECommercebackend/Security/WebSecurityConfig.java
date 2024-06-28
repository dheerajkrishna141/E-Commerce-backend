package com.ECommerce.ECommercebackend.Security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.ECommerce.ECommercebackend.Filters.CsrfTokenFilter;

@Configuration
public class WebSecurityConfig {

//	@Autowired
//	private JWTRequestFilter jwtRequestFilter;

//	@Autowired
//	private EmailVerificationFilter eFilter;

	@Autowired
	private CsrfTokenFilter csrfTokenFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		http.addFilterAfter(csrfTokenFilter, BasicAuthenticationFilter.class);
		
		http.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/user/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) );
		
		http.cors(cors -> {
			cors.configurationSource(request -> {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOriginPatterns(Collections.singletonList("*"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true); // this means that the server allows cookies to be included in cross
				// origin requests
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			});
		});
//	http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/products", "/user/register", "/user/verify", "/user/forgot", "/user/resetPassword")
					.permitAll();
			auth.requestMatchers("/user/orders/**").hasAnyRole("USER","ADMIN");
			auth.requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("USER","ADMIN");
			auth.requestMatchers("/admin/**").hasRole("ADMIN");

			auth.anyRequest().authenticated();
		});
		http.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
		return http.build();

	}

}
