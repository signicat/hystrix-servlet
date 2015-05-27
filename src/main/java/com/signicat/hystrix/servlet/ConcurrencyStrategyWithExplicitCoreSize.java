/*
 * Copyright (C) 2015 Signicat AS
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.signicat.hystrix.servlet;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency strategy which specifies an explicit core pool size. This is needed
 * because {@link com.netflix.hystrix.HystrixThreadPool.HystrixThreadPoolDefault} calls {@link
 * com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy#getThreadPool(com.netflix.hystrix.HystrixThreadPoolKey,
 * com.netflix.hystrix.strategy.properties.HystrixProperty, com.netflix.hystrix.strategy.properties.HystrixProperty,
 * com.netflix.hystrix.strategy.properties.HystrixProperty, java.util.concurrent.TimeUnit,
 * java.util.concurrent.BlockingQueue)} with {@link com.netflix.hystrix.HystrixThreadPoolProperties#coreSize()} for BOTH
 * size arguments, meaning that thread pools by default have a fixed size.
 *
 * @author Einar Rosenvinge &lt;einarmr@gmail.com&gt;
 */
public class ConcurrencyStrategyWithExplicitCoreSize extends HystrixConcurrencyStrategy {

    private static final ConcurrencyStrategyWithExplicitCoreSize INSTANCE =
            new ConcurrencyStrategyWithExplicitCoreSize();

    private ConcurrencyStrategyWithExplicitCoreSize() {
    }

    public static ConcurrencyStrategyWithExplicitCoreSize getInstance() {
        return INSTANCE;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(final HystrixThreadPoolKey threadPoolKey,
                                            final HystrixProperty<Integer> corePoolSize,
                                            final HystrixProperty<Integer> maximumPoolSize,
                                            final HystrixProperty<Integer> keepAliveTime,
                                            final TimeUnit unit,
                                            final BlockingQueue<Runnable> workQueue) {
        int minPoolSize = 10;
        minPoolSize = (minPoolSize < maximumPoolSize.get()) ? minPoolSize : maximumPoolSize.get();

        ThreadPoolExecutor executor = super.getThreadPool(threadPoolKey,
                                                          HystrixProperty.Factory.asProperty(minPoolSize),
                                                          maximumPoolSize,
                                                          keepAliveTime, unit, workQueue);
        executor.allowCoreThreadTimeOut(true);
        executor.prestartAllCoreThreads();
        return executor;
    }
}
