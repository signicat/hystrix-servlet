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

    // this might be worth keeping around if we ever decide to switch out HystrixThreadPoolDefault
    // with an implementation that requires this overloaded variant
    @Override
    public ThreadPoolExecutor getThreadPool(final HystrixThreadPoolKey threadPoolKey,
                                            final HystrixProperty<Integer> corePoolSize,
                                            final HystrixProperty<Integer> maximumPoolSize,
                                            final HystrixProperty<Integer> keepAliveTime,
                                            final TimeUnit unit,
                                            final BlockingQueue<Runnable> workQueue) {
        ThreadPoolExecutor executor = super.getThreadPool(threadPoolKey,
                                                          corePoolSize,
                                                          maximumPoolSize,
                                                          keepAliveTime,
                                                          unit,
                                                          workQueue);

        executor.allowCoreThreadTimeOut(true);
        executor.prestartAllCoreThreads();

        return executor;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixThreadPoolProperties threadPoolProperties) {
        ThreadPoolExecutor executor = super.getThreadPool(threadPoolKey, threadPoolProperties);

        executor.allowCoreThreadTimeOut(true);
        executor.prestartAllCoreThreads();

        return executor;
    }
}
