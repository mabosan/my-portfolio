package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginCheckFilter extends HttpFilter{
	public void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException,ServletException{
		String requestURL = request.getRequestURI();
		HttpSession session = request.getSession();
		if(requestURL.endsWith("/")||requestURL.endsWith("/login")||requestURL.endsWith(".css")) {
			chain.doFilter(request, response);
		}else if(session.getAttribute("user")==null) {
				response.sendRedirect("/spring_crud/");
				return;
			}else {
				chain.doFilter(request, response);
			}
		}
		
	
	}

