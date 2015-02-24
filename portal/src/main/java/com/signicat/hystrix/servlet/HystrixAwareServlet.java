package com.signicat.hystrix.servlet;

import javax.servlet.http.HttpServletRequest;


/**
 * Implement this interface if you want to decide which thread pool your servlet runs in if it is executed inside
 * {@link AsyncWrapperServlet}.
 *
 * @author Einar Rosenvinge &lt;einros@signicat.com&gt;
 * @see AsyncWrapperServlet
 */
public interface HystrixAwareServlet {

    public String getCommandGroupKey(HttpServletRequest request);
}
