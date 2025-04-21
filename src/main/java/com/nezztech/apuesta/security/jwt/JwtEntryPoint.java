/**
 * 
 */
package com.nezztech.apuesta.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint{

    
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        log.error("fail en el método commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
		
	}
	
	
	

}
