package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	

	@Bean
	public UserDetailsService getUserDetailService() {
		
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		
	http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
	.antMatchers("/user/**").hasRole("USER")
	.antMatchers("/**").permitAll().and().formLogin()
	
	// custom login page
	.loginPage("/signin")
	//url to submit the username and password(form)

	.loginProcessingUrl("/dologin")
	
	//anding page after successful login

	.defaultSuccessUrl("/user/index")
	//failure page
	.failureUrl("/login-fail")
	.and().csrf().disable();
		}
	
	
	
	
	
}
