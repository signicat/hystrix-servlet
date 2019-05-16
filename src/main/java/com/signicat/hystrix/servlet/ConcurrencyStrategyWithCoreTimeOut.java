/*
 * Copyright (C) 2015 Signicat AS
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.signicat.hystrix.servlet;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency strategy that ensures core threads are allowed to time out
 * as well as prestarting said core threads.
 *
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 */
public class ConcurrencyStrategyWithCoreTimeOut extends HystrixConcurrencyStrategy {

    private static final ConcurrencyStrategyWithCoreTimeOut INSTANCE =
            new ConcurrencyStrategyWithCoreTimeOut();

    private ConcurrencyStrategyWithCoreTimeOut() {
    }

    public static ConcurrencyStrategyWithCoreTimeOut getInstance() {
        return INSTANCE;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixThreadPoolProperties threadPoolProperties) {
        ThreadPoolExecutor executor = super.getThreadPool(threadPoolKey,
                                                          threadPoolProperties);

        // this change will be overridden by getExecutor in HystrixThreadpoolDefault
        int tempSize = Math.min(10, executor.getCorePoolSize());
        executor.setCorePoolSize(tempSize);
        // continue as usual
        executor.allowCoreThreadTimeOut(true);
        executor.prestartAllCoreThreads();

        return executor;
    }
}
