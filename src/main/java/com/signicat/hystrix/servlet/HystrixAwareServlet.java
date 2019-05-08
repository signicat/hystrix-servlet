/*
 * Copyright (C) 2015 Signicat AS
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.signicat.hystrix.servlet;

import javax.servlet.http.HttpServletRequest;


/**
 * Implement this interface if you want to decide which thread pool your servlet runs in if it is executed inside
 * {@link AsyncWrapperServlet}.
 *
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 * @see AsyncWrapperServlet
 */
public interface HystrixAwareServlet {

    String getCommandGroupKey(HttpServletRequest request);
}
