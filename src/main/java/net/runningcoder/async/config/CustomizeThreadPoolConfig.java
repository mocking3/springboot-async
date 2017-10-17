package net.runningcoder.async.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangmaocheng on 2017/10/16.
 */
@Configuration
public class CustomizeThreadPoolConfig {

    @Autowired
    private AppProperties appProperties;

    public static final String THREAD_POOL_MAIN = "main";
    public static final String THREAD_POOL_CUSTOMIZE = "customize";

    @Bean(name = THREAD_POOL_MAIN)
    public Executor mainExecutorService() {
        AppProperties.ThreadPool threadPool = appProperties.getThreadPools().get(THREAD_POOL_MAIN);
        return initExecutor(threadPool, new StringBuffer("worker-").append(THREAD_POOL_MAIN).append("-").toString());
    }

    @Bean(name = THREAD_POOL_CUSTOMIZE)
    public Executor customizeExecutorService() {
        AppProperties.ThreadPool threadPool = appProperties.getThreadPools().get(THREAD_POOL_CUSTOMIZE);
        return initExecutor(threadPool, new StringBuffer("worker-").append(THREAD_POOL_CUSTOMIZE).append("-").toString());
    }

    private Executor initExecutor(AppProperties.ThreadPool threadPool, String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        if (threadPool != null && threadPool.getCorePoolSize() > 0)
            executor.setCorePoolSize(threadPool.getCorePoolSize());
        if (threadPool != null && threadPool.getMaxPoolSize() > 0)
            executor.setMaxPoolSize(threadPool.getMaxPoolSize());
        if (threadPool != null && threadPool.getQueueCapacity() > 0)
            executor.setQueueCapacity(threadPool.getQueueCapacity());
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.afterPropertiesSet();
        return executor;
    }

}
