package com.epam.training.onlineshop.controller.filter;

import com.epam.training.onlineshop.entity.user.User;
import com.epam.training.onlineshop.entity.user.UserGroup;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Responsible for the protection of the administrative part of the site from unauthorized access
 *
 * @author Ihar Sidarenka
 * @version 0.1 22-Jun-19
 */
@WebFilter(filterName = "ServletSecurityFilter", urlPatterns = {"/"})
public class ServletSecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("User");
        RequestDispatcher dispatcher;

        if (user == null) {
            dispatcher = request.getServletContext().getRequestDispatcher("/html/login.html");
            dispatcher.forward(req, resp);
            return;
        } else if (user.getGroup() != UserGroup.ADMINISTRATOR) {
            dispatcher = request.getServletContext().getRequestDispatcher("/");
            dispatcher.forward(req, resp);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}