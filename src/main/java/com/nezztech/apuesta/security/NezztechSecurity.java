/**
 * 
 */
package com.nezztech.apuesta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nezztech.apuesta.security.jwt.JwtEntryPoint;
import com.nezztech.apuesta.security.jwt.JwtTokenFilter;
import com.nezztech.apuesta.security.service.PasswordEnconderService;
import com.nezztech.apuesta.security.service.UserDetailsServiceImpl;

/**
 * @author MROSCAR
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NezztechSecurity extends WebSecurityConfigurerAdapter {

	   @Autowired
	    UserDetailsServiceImpl userDetailsService;

	    @Autowired
	    JwtEntryPoint jwtEntryPoint; 
	  
	    @Value("${app.seguridad.habilitada}")
	    private Boolean seguridadHabilitada;    
	    
	    
	    @Bean
	    public JwtTokenFilter jwtTokenFilter(){
	        return new JwtTokenFilter();
	    }

	    
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	    	//return new BCryptPasswordEncoder();
	        return new PasswordEnconderService();
	    }    
	    

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Override
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }
	    
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	String path="/**";
	    	
	    	if(seguridadHabilitada) {
	    		path="/auth/**";
	    	}
	    	
	        http.cors().and().csrf().disable()
	                .authorizeRequests()
	                .antMatchers(path, "/swagger-ui/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    }    
	    
}
