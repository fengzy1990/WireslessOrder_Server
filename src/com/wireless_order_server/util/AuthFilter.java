package com.wireless_order_server.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 统一验证
 * 
 * @author sxmws
 * 
 */

public class AuthFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestURI = request.getRequestURI();
		System.out.println("requestURI" + requestURI);
		String currentURI = requestURI.substring(requestURI.indexOf("/", 1),
				requestURI.length());
		System.out.println("currentURI=" + currentURI);
		if (!"/login.jsp".equals(currentURI)
				&& !"/register.jsp".equals(currentURI)) {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("username") == null) {
				// System.out.println("username------>" +
				// session.getAttribute("username"));
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("---------filter init--------");

	}

}
