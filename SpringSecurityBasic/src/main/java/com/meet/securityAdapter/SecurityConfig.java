package com.meet.securityAdapter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
 * This Class work like adapter
 * 
 * Here you can control you security like you can put restriction on link,
 * pass user name and password and main benefit is you don't need to manage session.
 * 
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// password encoder is userful in bcrypt to plain text
		
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
		
		
		/*
		 * if you have different table you can get username and password 
		 * by using 
		 * usersByUsernameQuery() and authoritiesByUsernameQuery()
		 * */ 
		
		
	}

	/*
	 * Here You can manage link or put authentication particular link. 
	 * */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/").permitAll().
			antMatchers("/HomePage").authenticated().and()
			.formLogin().loginPage("/logIn").loginProcessingUrl("/logInurl");
		
	}

	
}
