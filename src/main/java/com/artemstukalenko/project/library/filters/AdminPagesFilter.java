package com.artemstukalenko.project.library.filters;

import com.artemstukalenko.project.library.entity.User;
import com.artemstukalenko.project.library.view.FirstView;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPagesFilter implements Filter {

    private FirstView errorMessageManager = new FirstView();

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User potentialTrespasser = (User) request.getSession().getAttribute("currentUser");

        if (potentialTrespasser.getUserDetails().getAuthorityString().equals("ADMIN")) {
            filterChain.doFilter(request, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, errorMessageManager.getErrorMessageForbidden());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
