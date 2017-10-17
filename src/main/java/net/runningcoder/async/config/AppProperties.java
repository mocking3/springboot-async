package net.runningcoder.async.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wangmaocheng on 2017/10/16.
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Map<String, ThreadPool> threadPools;

    @Data
    public static class ThreadPool {
        private int corePoolSize;
        private int maxPoolSize;
        private int keepAliveSeconds;
        private int queueCapacity;
    }
}
