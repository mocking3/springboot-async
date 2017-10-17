package net.runningcoder.async.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangmaocheng on 2017/10/16.
 */
@Configuration
public class CustomizeThreadPoolConfig {

    @Autowired
    private AppProperties appProperties;

    public static final String THREAD_POOL_CUSTOMIZE = "customize";

    @Bean(name = THREAD_POOL_CUSTOMIZE)
    public ThreadPoolExecutorFactoryBean executorService() {
        AppProperties.ThreadPool threadPool = appProperties.getThreadPools().get(THREAD_POOL_CUSTOMIZE);

        ThreadPoolExecutorFactoryBean bean = new ThreadPoolExecutorFactoryBean();
        if (threadPool.getCorePoolSize() > 0)
            bean.setCorePoolSize(threadPool.getCorePoolSize());
        if (threadPool.getMaxPoolSize() > 0)
            bean.setMaxPoolSize(threadPool.getMaxPoolSize());
        if (threadPool.getQueueCapacity() > 0)
            bean.setQueueCapacity(threadPool.getQueueCapacity());
        bean.setThreadNamePrefix("worker-" + THREAD_POOL_CUSTOMIZE + "-");
        bean.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        bean.afterPropertiesSet();
        return bean;
    }
}
