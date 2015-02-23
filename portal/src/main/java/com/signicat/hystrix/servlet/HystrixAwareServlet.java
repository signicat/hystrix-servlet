package com.signicat.hystrix.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * Extend this class if you want to decide which thread pool your servlet runs in
 * if it is executed inside {@link AsyncWrapperServlet}.
 *
 * @see AsyncWrapperServlet
 */
public abstract class HystrixAwareServlet extends HttpServlet {
    public abstract String getCommandGroupKey(HttpServletRequest request);
}
