package net.runningcoder.async.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangmaocheng on 2017/10/16.
 */
@Configuration
public class DefaultThreadPoolConfig implements AsyncConfigurer {

    @Autowired
    private AppProperties appProperties;

    public static final String THREAD_POOL_MAIN = "main";

    @Override
    public Executor getAsyncExecutor() {
        AppProperties.ThreadPool threadPool = appProperties.getThreadPools().get(THREAD_POOL_MAIN);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        if (threadPool != null && threadPool.getCorePoolSize() > 0)
            executor.setCorePoolSize(threadPool.getCorePoolSize());
        if (threadPool != null && threadPool.getMaxPoolSize() > 0)
            executor.setMaxPoolSize(threadPool.getMaxPoolSize());
        if (threadPool != null && threadPool.getQueueCapacity() > 0)
            executor.setQueueCapacity(threadPool.getQueueCapacity());
        executor.setThreadNamePrefix("worker-" + THREAD_POOL_MAIN + "-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.afterPropertiesSet();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
