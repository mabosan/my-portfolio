package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Employee;

@Component
public class AccountCheckFilter extends HttpFilter {
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURL = request.getRequestURI();
		HttpSession session = request.getSession();
		Integer sessionUserAuthority = -1;
		Integer sessionUserEmpId = -1;
		Employee sessionUser = ((Employee) session.getAttribute("user"));
		System.out.println(sessionUser);
		if (sessionUser != null) {
			sessionUserAuthority = sessionUser.getAuthority();
			sessionUserEmpId = sessionUser.getEmpId();
		}
		// employeeFormがnullの場合の処理
		if (sessionUserAuthority == 1 && sessionUserEmpId != sessionUser.getEmpId()&&requestURL.contains("/update/")) {
			response.sendRedirect("/spring_crud/");
			return;
		} else if(sessionUserAuthority == 1&&(requestURL.contains("/regist/") || requestURL.contains("/delete/"))){
			response.sendRedirect("/spring_crud/");
		}else {
			chain.doFilter(request, response);
		}
	}
}
